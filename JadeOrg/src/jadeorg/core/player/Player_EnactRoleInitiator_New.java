package jadeorg.core.player;

import jade.core.AID;
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
import jadeorg.proto_new.jadeorgextensions.SendAgreeOrRefuse;

/**
 * An 'Enact role' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
class Player_EnactRoleInitiator_New extends Party {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "enact-role-initiator-new";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The organization AID. */
    private AID organizationAID;

    /** The role name */
    private String roleName;
    
    private String[] requirements;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Player_EnactRoleInitiator_New(AID organization, String roleName) {
        super(NAME);
        // ----- Preconditions -----
        assert organization != null;
        assert roleName != null && !roleName.isEmpty();
        // -------------------------

        this.organizationAID = organization;
        this.roleName = roleName;

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
        State sendEnactRequest = new SendEnactRequest();
        State receiveRequirementsInform = new ReceiveRequirementsInform();
        State sendRequirementsReply = new SendRequirementsReply();
        State receiveRoleAID = new ReceiveRoleAID();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(sendEnactRequest);
        registerState(receiveRequirementsInform);
        registerState(sendRequirementsReply);
        registerState(receiveRoleAID);
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions (NEW).
        sendEnactRequest.registerDefaultTransition(receiveRequirementsInform);

        receiveRequirementsInform.registerTransition(ReceiveRequirementsInform.SUCCESS, sendRequirementsReply);
        receiveRequirementsInform.registerTransition(ReceiveRequirementsInform.FAILURE, failureEnd);
        
        sendRequirementsReply.registerTransition(SendRequirementsReply.AGREE, receiveRoleAID);
        sendRequirementsReply.registerTransition(SendRequirementsReply.REFUSE, failureEnd);

        receiveRoleAID.registerDefaultTransition(successEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Send enact request' active state.
     * A state in which the 'Enact' request is sent.
     */
    private class SendEnactRequest extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-enact-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendEnactRequest() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Player)myAgent).logInfo("Sending enact request.");
        }
        
        @Override
        protected void onSender() {
            // Create the 'Enact request' message.
            EnactRequestMessage message = new EnactRequestMessage();
            message.setRoleName(roleName);

            // Send the message.
            send(message, organizationAID);
        }
        
        @Override
        protected void onExit() {
            ((Player)myAgent).logInfo("Enact request sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive requirements info' passive state.
     * A state in which the 'Requirements' info is received.
     */
    private class ReceiveRequirementsInform extends MultiReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        // ----- Exit values -----
        static final int SUCCESS = 1;
        static final int FAILURE = 2;
        // -----------------------
        
        private static final String NAME = "receive-requirements-inform";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveRequirementsInform() {
            super(NAME);
            
            addReceiver(new ReceiveRequirementsInform_Receiver());
            addReceiver(new ReceiveFailure(FAILURE, organizationAID));
            buildFSM();
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            ((Player)myAgent).logInfo("Receiving requirements info.");
        }

        @Override
        protected void onExit() {
            ((Player)myAgent).logInfo("Requirements info received.");
        }

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        private class ReceiveRequirementsInform_Receiver extends InnerReceiverState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "receiver";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveRequirementsInform_Receiver() {
                super(NAME, SUCCESS, organizationAID);
            }
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Methods">
            
            @Override
            public void action() {
                //System.out.println("----- " + getParent().getBehaviourName() + " RECEIVER -----");
                // Receive the 'Requirements inform' message.
                RequirementsInformMessage message = new RequirementsInformMessage();
                boolean messageReceived = receive(message, organizationAID);
                
                // Process the message.
                if (messageReceived) {
                    //System.out.println("----- RECEIVED -----");
                    requirements = message.getRequirements();
                    setExitValue(RECEIVED);
                } else {
                    //System.out.println("----- NOT-RECEIVED -----");
                    setExitValue(NOT_RECEIVED);
                }
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send requirements reply' (multi sender) state.
     * A state in which the 'Agree' or 'Refuse' message is sent.
     */
    private class SendRequirementsReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-requirements-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendRequirementsReply() {
            super(NAME, organizationAID);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Player)myAgent).logInfo("Sending requirements reply.");
        }
        
        @Override
        protected int onManager() {
            if (((Player)myAgent).evaluateRequirements(requirements)) {
                // The player meets the requirements.
                return AGREE;
            } else {
                // The player does not meet the requirements.
                return REFUSE;
            }
        }
        
        @Override
        protected void onExit() {
            ((Player)myAgent).logInfo("Requirements reply sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive role AID' passive state.
     * A state in which the 'Role AID' requirementsInformMessage is received.
     */
    private class ReceiveRoleAID extends SingleReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Fields">

        private static final String NAME = "receive-role-aid";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveRoleAID() {
            super(NAME, organizationAID);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Player)myAgent).logInfo("Receiving role AID.");
        }
        
        @Override
        protected int onReceiver() {
            RoleAIDMessage message = new RoleAIDMessage();
            boolean messageReceived = receive(message, organizationAID);      
            
            if (messageReceived) {
                AID roleAID = message.getRoleAID();
                ((Player)myAgent).knowledgeBase.enactRole(roleName, roleAID, organizationAID.getLocalName(), organizationAID);
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            ((Player)myAgent).logInfo("Role AID received.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'SuccessEnd successEnd' state.
     * A state in which the 'Enact' protocol initiator party ends.
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
            ((Player)myAgent).logInfo("Enact role initiator party succeeded.");
        }

        // </editor-fold>
    }

    /**
     * The 'Fail successEnd' state.
     * A state in which the 'Enact' protocol initiator party ends.
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
            ((Player)myAgent).logInfo("Enact role initiator party failed.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
