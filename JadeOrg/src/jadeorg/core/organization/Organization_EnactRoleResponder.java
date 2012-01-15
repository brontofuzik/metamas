package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jadeorg.proto.AssertPreconditions;
import jadeorg.proto.Protocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RequirementsInformMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RoleAIDMessage;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.ReceiveAgreeOrRefuse;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.SendSuccessOrFailure;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * An 'Enact role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
class Organization_EnactRoleResponder extends ResponderParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private ACLMessage aclMessage;
    
    private AID playerAID;

    private String roleName;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Organization_EnactRoleResponder(ACLMessage aclMessage) {
        super(aclMessage);
        
        this.aclMessage = aclMessage;
        this.playerAID = aclMessage.getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    public Protocol getProtocol() {
        return EnactRoleProtocol.getInstance();
    }
    
    private Organization getMyOrganization() {
        return (Organization)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void buildFSM() {
        // ----- States -----
        State assertPreconditions = new MyAssertPreconditions();
        State receiveEnactRequest = new ReceiveEnactRequest();
        State sendRequirementsInform = new SendRequirementsInform();
        State receiveRequirementsReply = new ReceiveRequirementsReply();
        State sendRoleAID = new SendRoleAID();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(assertPreconditions);
        
        registerState(receiveEnactRequest);
        registerState(sendRequirementsInform);
        registerState(receiveRequirementsReply);
        registerState(sendRoleAID);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        assertPreconditions.registerTransition(MyAssertPreconditions.SUCCESS, receiveEnactRequest);
        assertPreconditions.registerTransition(MyAssertPreconditions.FAILURE, failureEnd);
        
        receiveEnactRequest.registerDefaultTransition(sendRequirementsInform);

        sendRequirementsInform.registerTransition(SendRequirementsInform.SUCCESS, receiveRequirementsReply);
        sendRequirementsInform.registerTransition(SendRequirementsInform.FAILURE, failureEnd);
        
        receiveRequirementsReply.registerTransition(ReceiveRequirementsReply.AGREE, sendRoleAID);
        receiveRequirementsReply.registerTransition(ReceiveRequirementsReply.REFUSE, failureEnd);   

        sendRoleAID.registerDefaultTransition(successEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyAssertPreconditions extends AssertPreconditions {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected boolean preconditionsSatisfied() {
            getMyOrganization().logInfo(String.format(
                "Responding to the 'Enact role' protocol (id = %1$s).",
                aclMessage.getConversationId()));
            return true;
        }
        
        // </editor-fold>
    }
    
    private class ReceiveEnactRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            EnactRequestMessage message = new EnactRequestMessage();
            message.parseACLMessage(aclMessage);
            roleName = message.getRoleName();
        }
        
        // </editor-fold>        
    }
    
    private class SendRequirementsInform extends SendSuccessOrFailure {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return playerAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyOrganization().logInfo("Sending requirements inform.");
        }
        
        @Override
        protected int onManager() {
            if (getMyOrganization().roles.containsKey(roleName)) {
                // The role is defined for this organizaiton.
                if (!getMyOrganization().knowledgeBase.isRoleEnacted(roleName)) {
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
        protected void onSuccessSender() {
            // Create the 'Requirements inform' message.
            RequirementsInformMessage requirementsInformMessage = new RequirementsInformMessage();
            requirementsInformMessage.setRequirements(getMyOrganization().requirements.get(roleName));

            // Send the message.
            send(requirementsInformMessage, playerAID);
        }

        @Override
        protected void onExit() {
            getMyOrganization().logInfo("Requirements inform sent.");
        }
      
        // </editor-fold>
    }
    
    private class ReceiveRequirementsReply extends ReceiveAgreeOrRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return playerAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyOrganization().logInfo("Receiving requirements reply.");
        }

        @Override
        protected void onExit() {
            getMyOrganization().logInfo("Requirements reply received.");
        }
        
        // </editor-fold>
    }
    
    private class SendRoleAID extends SingleSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return playerAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyOrganization().logInfo("Sending role AID.");
        }
        
        @Override
        protected void onSingleSender() {
            getMyOrganization().logInfo("Creating role agent.");

            Role role = createRoleAgent(roleName, roleName);
            role.setPlayerAID(playerAID);
            startRoleAgent(role);
            getMyOrganization().knowledgeBase.updateRoleIsEnacted(roleName, role.getAID(), playerAID);

            getMyOrganization().logInfo("Role agent created.");
            
            // Create the 'RoleAID' JadeOrg message.
            RoleAIDMessage roleAIDMessage = new RoleAIDMessage();
            roleAIDMessage.setRoleAID(role.getAID());

            // Send the 'RoleAID' JadeOrg message.
            send(roleAIDMessage, playerAID);        
        }

        @Override
        protected void onExit() {
            getMyOrganization().logInfo("Role AID sent.");
        }
        
        // ---------- PRIVATE ----------

        /**
         * Create a role agent.
         * @param roleClassName the name of the role agent class.
         * @param roleName the name of the role agent instance.
         * @return the role agent.
         */
        private Role createRoleAgent(String roleClassName, String roleName) {
            Class roleClass = getMyOrganization().roles.get(roleClassName);
            Class organizationClass = myAgent.getClass();

            // Get the role constructor.
            Constructor roleConstructor = null;
            try {
                roleConstructor = roleClass.getConstructor(organizationClass);
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (SecurityException ex) {
                ex.printStackTrace();
            }
            
            // Instantiate the role.
            Role role = null;
            try {
                role = (Role)roleConstructor.newInstance(myAgent);
            } catch (InstantiationException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
            
            // Initialize the role.
            role.setRoleName(roleName);
            role.setMyOrganization((Organization)myAgent);
            
            return role;
        }

        private void startRoleAgent(Role role) {
            AgentController agentController = null;
            try {
                agentController = myAgent.getContainerController().acceptNewAgent(roleName, role);
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
            getMyOrganization().logInfo("Enact role responder party succeeded.");
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
            getMyOrganization().logInfo("Enact role responder party failed.");
        }

        // </editor-fold>        
    }
    
    // </editor-fold>
}
