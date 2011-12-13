package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto_new.State;
import jadeorg.proto_new.toplevel.MultiReceiverState;
import jadeorg.proto_new.toplevel.MultiSenderState;
import jadeorg.proto_new.toplevel.SimpleState;
import jadeorg.proto_new.toplevel.SingleReceiverState;
import jadeorg.proto_new.toplevel.SingleSenderState;

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
        registerLastState(successEnd);
        
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
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Organization)myAgent).logInfo("Receiving enact request.");
        }
        
        @Override
        protected void onReceiver() {
            EnactRequestMessage enactRequestMessage = (EnactRequestMessage)
                receive(EnactRequestMessage.class, playerAID);
            
            if (enactRequestMessage != null) {
                roleName = enactRequestMessage.getRoleName();
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
            addSender(FAILURE, this.new SendFailure());
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
        
        private class SendRequirementsInform_Sender extends BottomLevelSenderState {

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
                throw new UnsupportedOperationException("Not supported yet.");
            }
            
            // </editor-fold>
        }
        
        // </editor-fold>
    }
    
    private class ReceiveRequirementsReply extends MultiReceiverState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int AGREE = 1;
        public static final int REFUSE = 2;
        // -----------------------
        
        private static final String NAME = "receive-requirements-reply";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveRequirementsReply() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void onExit() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>
    }
    
    private class SendRoleAID extends SingleSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-requirements-reply";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendRoleAID() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        @Override
        protected void onSender() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void onExit() {
            throw new UnsupportedOperationException("Not supported yet.");
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
