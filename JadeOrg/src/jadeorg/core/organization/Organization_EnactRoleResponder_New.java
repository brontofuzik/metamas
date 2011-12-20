package jadeorg.core.organization;

import jade.core.AID;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RequirementsInformMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.RoleAIDMessage;
import jadeorg.proto_new.jadeextensions.State;
import jadeorg.proto_new.MultiReceiverState;
import jadeorg.proto_new.MultiSenderState;
import jadeorg.proto_new.SimpleState;
import jadeorg.proto_new.SingleReceiverState;
import jadeorg.proto_new.SingleSenderState;
import jadeorg.proto_new.jadeorgextensions.ReceiveAgreeOrRefuse;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * An 'Enact role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
class Organization_EnactRoleResponder_New extends Party {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "enact-role-responder-new";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private AID playerAID;

    private String roleName;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Organization_EnactRoleResponder_New(AID playerAID) {
        super(NAME);
        // ----- Preconditions -----
        assert playerAID != null;
        // -------------------------
        
        this.playerAID = playerAID;
        registerStatesAndTransitions();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    @Override
    protected Protocol getProtocol() {
        return EnactRoleProtocol.getInstance();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndTransitions() {
        // ----- States -----
        State receiveEnactRequest = new ReceiveEnactRequest();
        State sendRequirementsInform = new SendRequirementsInform();
        State receiveRequirementsReply = new ReceiveRequirementsReply();
        State sendRoleAID = new SendRoleAID();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(receiveEnactRequest);
        registerState(sendRequirementsInform);
        registerState(receiveRequirementsReply);
        registerState(sendRoleAID);
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        receiveEnactRequest.registerDefaultTransition(sendRequirementsInform);

        sendRequirementsInform.registerTransition(SendRequirementsInform.SUCCESS, receiveRequirementsReply);
        sendRequirementsInform.registerTransition(SendRequirementsInform.FAILURE, failureEnd);
        
        receiveRequirementsReply.registerTransition(ReceiveRequirementsReply.AGREE, sendRoleAID);
        receiveRequirementsReply.registerTransition(ReceiveRequirementsReply.REFUSE, failureEnd);   

        sendRoleAID.registerDefaultTransition(successEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class ReceiveEnactRequest extends SingleReceiverState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-enact-request";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveEnactRequest() {
            super(NAME, playerAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Organization)myAgent).logInfo("Receiving enact request.");
        }
        
        @Override
        protected int onReceiver() {
            // Receive the 'Enact request' message.
            EnactRequestMessage message = new EnactRequestMessage();
            boolean messageReceived = receive(message, playerAID);
            
            // Process the message.
            if (messageReceived) {
                roleName = message.getRoleName();
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            ((Organization)myAgent).logInfo("Enact request received.");
        }
        
        // </editor-fold>
        
    }
    
    private class SendRequirementsInform extends MultiSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit value -----
        public static final int SUCCESS = 1;
        public static final int FAILURE = 2;
        // ----------------------
        
        private static final String NAME = "send-requirements-inform";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRequirementsInform() {
            super(NAME);
            
            addSender(SUCCESS, this.new SendRequirementsInform_Sender());
            addSender(FAILURE, this.new SendFailure(playerAID));
            buildFSM();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            ((Organization)myAgent).logInfo("Sending requirements inform.");
        }
        
        @Override
        protected int onManager() {
            if (((Organization)myAgent).roles.containsKey(roleName)) {
                // The role is defined for this organizaiton.
                if (!((Organization)myAgent).knowledgeBase.isRoleEnacted(roleName)) {
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
        protected void onExit() {
            ((Organization)myAgent).logInfo("Requirements inform sent.");
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        private class SendRequirementsInform_Sender extends InnerSenderState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">
            
            private static final String NAME = "sener";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            SendRequirementsInform_Sender() {
                super(NAME);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                // Create the 'Requirements inform' message.
                RequirementsInformMessage requirementsInformMessage = new RequirementsInformMessage();
                requirementsInformMessage.setRequirements(((Organization)myAgent).requirements.get(roleName));

                // Send the message.
                send(requirementsInformMessage, playerAID);
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementsReply extends ReceiveAgreeOrRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-requirements-reply";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveRequirementsReply() {
            super(NAME, playerAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            ((Organization)myAgent).logInfo("Receiving requirements reply.");
        }

        @Override
        protected void onExit() {
            ((Organization)myAgent).logInfo("Requirements reply received.");
        }
        
        // </editor-fold>
    }
    
    private class SendRoleAID extends SingleSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-role-aid";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRoleAID() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Organization)myAgent).logInfo("Sending role AID.");
        }
        
        @Override
        protected void onSender() {
            ((Organization)myAgent).logInfo("Creating role agent.");

            Role role = createRoleAgent(roleName, roleName);
            role.setPlayerAID(playerAID);
            startRoleAgent(role);
            ((Organization)myAgent).knowledgeBase.updateRoleIsEnacted(role, playerAID);

            ((Organization)myAgent).logInfo("Role agent created.");
            
            // Create the 'RoleAID' JadeOrg message.
            RoleAIDMessage roleAIDMessage = new RoleAIDMessage();
            roleAIDMessage.setRoleAID(role.getAID());

            // Send the 'RoleAID' JadeOrg message.
            send(roleAIDMessage, playerAID);        
        }

        @Override
        protected void onExit() {
            ((Organization)myAgent).logInfo("Role AID sent.");
        }
        
        // ---------- PRIVATE ----------

        /**
         * Create a role agent.
         * @param roleClassName the name of the role agent class.
         * @param roleName the name of the role agent instance.
         * @return the role agent.
         */
        private Role createRoleAgent(String roleClassName, String roleName) {
            Class roleClass = ((Organization)myAgent).roles.get(roleClassName);
            //System.out.println("ROLE CLASS: " + roleClass);
            Class organizationClass = myAgent.getClass();
            //System.out.println("ORGANIZATION CLASS: " + organizationClass);

            Constructor roleConstructor = null;
            try {
                roleConstructor = roleClass.getConstructor(organizationClass);
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            } catch (SecurityException ex) {
                ex.printStackTrace();
            }
            //System.out.println("CTOR: " + roleConstructor.getParameterTypes());
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
            //System.out.println("ROLE: " + role);
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
    private class SuccessEnd extends SimpleState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "success-end";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SuccessEnd() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Organization)myAgent).logInfo("Enact role responder party succeeded.");
        }

        // </editor-fold>           
    }

    /**
     * The 'Failure end' state.
     */
    private class FailureEnd extends SimpleState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "failure-end";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        FailureEnd() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Organization)myAgent).logInfo("Enact role responder party failed.");
        }

        // </editor-fold>        
    }
    
    // </editor-fold>
}
