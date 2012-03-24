package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import thespian4jade.behaviours.ExitValueState;
import thespian4jade.protocols.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import thespian4jade.protocols.organizationprotocol.enactroleprotocol.ResponsibilitiesInformMessage;
import thespian4jade.protocols.organizationprotocol.enactroleprotocol.RoleAIDMessage;
import thespian4jade.behaviours.jadeextensions.IState;
import thespian4jade.behaviours.senderstates.SingleSenderState;
import thespian4jade.behaviours.receiverstate.ReceiveAgreeOrRefuse;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.senderstates.SendSuccessOrFailure;
import thespian4jade.behaviours.jadeextensions.OneShotBehaviourState;
import thespian4jade.core.Event;
import thespian4jade.protocols.ProtocolRegistry_StaticClass;
import thespian4jade.protocols.Protocols;
import thespian4jade.utililites.ClassHelper;

/**
 * An 'Enact role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public class Organization_EnactRole_ResponderParty extends ResponderParty<Organization> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player requesting the role enactment; more precisely its AID.
     * The initiator party.
     */
    private AID player;

    private String roleName;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Organization_EnactRole_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.ENACT_ROLE_PROTOCOL), aclMessage);
       
        player = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new Initialize();
        IState receiveEnactRequest = new ReceiveEnactRequest();
        IState sendResponsibilitiesInform = new SendResponsibilitiesInform();
        IState receiveResponsibilitiesReply = new ReceiveResponsibilitiesReply();
        IState sendRoleAID = new SendRoleAID();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
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
        initialize.registerTransition(Initialize.OK, receiveEnactRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);     
        receiveEnactRequest.registerDefaultTransition(sendResponsibilitiesInform);
        sendResponsibilitiesInform.registerTransition(SendResponsibilitiesInform.SUCCESS, receiveResponsibilitiesReply);
        sendResponsibilitiesInform.registerTransition(SendResponsibilitiesInform.FAILURE, failureEnd);       
        receiveResponsibilitiesReply.registerTransition(ReceiveResponsibilitiesReply.AGREE, sendRoleAID);
        receiveResponsibilitiesReply.registerTransition(ReceiveResponsibilitiesReply.REFUSE, failureEnd);   
        sendRoleAID.registerDefaultTransition(successEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends ExitValueState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int OK = 1;
        public static final int FAIL = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int doAction() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Enact role' protocol (id = %1$s) responder party started.",
                getProtocolId()));
            
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
            return new AID[] { player };
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
                if (!getMyAgent().knowledgeBase.query().isRoleEnacted(roleName)
                    || getMyAgent().roles.get(roleName).getMultiplicity() == Multiplicity.MULTIPLE) {
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
            return new AID[] { player };
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
            return new AID[] { player };
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
            
            // Create the role agent.
            Class roleClass = getMyAgent().roles.get(roleName).getRoleClass();
            Role role = ClassHelper.instantiateClass(roleClass);
            
            // Link the position to its organization.
            getMyAgent().addPosition(role);
            
            // Link the position to its player.
            role.setPlayerAID(player);
            
            // Start the role agent.
            startRoleAgent(role);
            
            // Update the knowledge base.
            getMyAgent().knowledgeBase.update().roleIsEnacted(roleName, role.getAID(), player);
            
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
     * A state in which the 'Enact role' responder party succeedes.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // Publish the 'Role enacted' event.
            getMyAgent().publishEvent(Event.ROLE_ENACTED, roleName, player);
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Enact role' protocol (id = %1$s) responder party succeeded.",
                getProtocolId()));
        }

        // </editor-fold>           
    }

    /**
     * The 'Failure end' state.
     * A state in which the 'Enact role' responder party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Enact role' protocol (id = %1$s) responder party failed.",
                getProtocolId()));
        }

        // </editor-fold>        
    }
    
    // </editor-fold>
}
