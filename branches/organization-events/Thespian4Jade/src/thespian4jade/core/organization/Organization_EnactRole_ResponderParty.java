package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.ResponsibilitiesInformMessage;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.RoleAIDMessage;
import thespian4jade.proto.jadeextensions.State;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.ReceiveAgreeOrRefuse;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SendSuccessOrFailure;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * An 'Enact role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public class Organization_EnactRole_ResponderParty extends ResponderParty<Organization> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;

    private String roleName;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Organization_EnactRole_ResponderParty(ACLMessage aclMessage) {
        super(EnactRoleProtocol.getInstance(), aclMessage);
       
        playerAID = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
        State receiveEnactRequest = new ReceiveEnactRequest();
        State sendResponsibilitiesInform = new SendResponsibilitiesInform();
        State receiveResponsibilitiesReply = new ReceiveResponsibilitiesReply();
        State sendRoleAID = new SendRoleAID();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(receiveEnactRequest);
        registerState(sendResponsibilitiesInform);
        registerState(receiveResponsibilitiesReply);
        registerState(sendRoleAID);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerTransition(MyInitialize.OK, receiveEnactRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        
        receiveEnactRequest.registerDefaultTransition(sendResponsibilitiesInform);

        sendResponsibilitiesInform.registerTransition(SendResponsibilitiesInform.SUCCESS, receiveResponsibilitiesReply);
        sendResponsibilitiesInform.registerTransition(SendResponsibilitiesInform.FAILURE, failureEnd);
        
        receiveResponsibilitiesReply.registerTransition(ReceiveResponsibilitiesReply.AGREE, sendRoleAID);
        receiveResponsibilitiesReply.registerTransition(ReceiveResponsibilitiesReply.REFUSE, failureEnd);   

        sendRoleAID.registerDefaultTransition(successEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            getMyAgent().logInfo(String.format(
                "Responding to the 'Enact role' protocol (id = %1$s).",
                getACLMessage().getConversationId()));
            return OK;
        }
        
        // </editor-fold>
    }
    
    private class ReceiveEnactRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            EnactRequestMessage message = new EnactRequestMessage();
            message.parseACLMessage(getACLMessage());
            roleName = message.getRoleName();
        }
        
        // </editor-fold>        
    }
    
    private class SendResponsibilitiesInform
        extends SendSuccessOrFailure<ResponsibilitiesInformMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending responsibilities inform.");
        }
        
        @Override
        protected int onManager() {
            if (getMyAgent().roles.containsKey(roleName)) {
                // The role is defined for this organizaiton.
                if (!getMyAgent().knowledgeBase.isRoleEnacted(roleName)) {
                    // The role is not yet enacted.
                    return SUCCESS;
                } else {
                    // The role is already enacted.
                    return FAILURE;
                }
            } else {
                // The role is not defined for this organization.
                return FAILURE;
            }
        }
        
        @Override
        protected ResponsibilitiesInformMessage prepareMessage() {
            // Create the 'Responsibilities inform' message.
            ResponsibilitiesInformMessage message = new ResponsibilitiesInformMessage();
            message.setResponsibilities(getMyAgent().roles.get(roleName).getResponsibilities());
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibilities inform sent.");
        }
      
        // </editor-fold>
    }
    
    private class ReceiveResponsibilitiesReply extends ReceiveAgreeOrRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving responsibilities reply.");
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Responsibilities reply received.");
        }
        
        // </editor-fold>
    }
    
    private class SendRoleAID extends SingleSenderState<RoleAIDMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending role AID.");
        }
        
        @Override
        protected RoleAIDMessage prepareMessage() {
            getMyAgent().logInfo("Creating role agent.");
            
            // Create the role agent and associate it with the player.
            Role role = createRoleAgent(roleName);
            role.setPlayerAID(playerAID);
            
            startRoleAgent(role);
            
            // Update the knowledge base.
            getMyAgent().knowledgeBase.updateRoleIsEnacted(roleName, role.getAID(), playerAID);
            
            getMyAgent().logInfo("Role agent created.");
            
            // Create the 'RoleAID' JadeOrg message.
            RoleAIDMessage roleAIDMessage = new RoleAIDMessage();
            roleAIDMessage.setRoleAID(role.getAID());

            return roleAIDMessage;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Role AID sent.");
        }
        
        // ---------- PRIVATE ----------

        /**
         * Create a role agent.
         * @param roleClassName the name of the role agent class.
         * @param roleInstanceName the name of the role agent instance.
         * @return the role agent.
         */
        private Role createRoleAgent(String roleClassName) {
            // Get the role class.
            Class roleClass = getMyAgent().roles.get(roleClassName).getRoleClass();
            //System.out.println("----- ROLE CLASS: " + roleClass + " -----");
            
            // Get the role constructor.
            Constructor roleConstructor = null;
            try {
                roleConstructor = roleClass.getConstructor();
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (SecurityException ex) {
                ex.printStackTrace();
            }
            //System.out.println("----- ROLE CONSTRUCTOR: " + roleConstructor + " -----");
            
            // Instantiate the role agent.
            Role roleAgent = null;
            try {
                roleAgent = (Role)roleConstructor.newInstance();
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
            //System.out.println("----- ROLE: " + roleAgent + " -----");
            
            // Associate the role agent with the organization agent.
            roleAgent.setMyOrganization(getMyAgent());
            
            return roleAgent;
        }

        private void startRoleAgent(Role roleAgent) {
            //System.out.println("----- STARTING ROLE AGENT: " + roleAgent.getNickname() + " -----");
            try {
                AgentController agentController = myAgent.getContainerController()
                    .acceptNewAgent(roleAgent.getNickname(), roleAgent);
                agentController.start();
            } catch (StaleProxyException ex) {
                ex.printStackTrace();
            }    
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' state.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Enact role responder party succeeded.");
        }

        // </editor-fold>           
    }

    /**
     * The 'Failure end' state.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Enact role responder party failed.");
        }

        // </editor-fold>        
    }
    
    // </editor-fold>
}
