package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.SimpleState;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.SendAgreeOrRefuse;

/**
 * An 'Activate role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-10
 * @version %I% %G%
 */
public class Role_ActivateRoleResponder_New extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "activate-role-responder-new";

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private AID playerAID;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Role_ActivateRoleResponder_New(AID playerAID) {
        super(NAME);
        // ----- Preconditions -----
        assert playerAID != null;
        // -------------------------
        
        this.playerAID = playerAID;
        registerStatesAndTransitions();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    public Protocol getProtocol() {
        return ActivateRoleProtocol.getInstance();
    }
    
    private Role getMyRole() {
        return (Role)myAgent;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndTransitions() {
        // ----- States -----
        State receiveActivateRequest = new ReceiveActivateRequest();
        State sendActivateReply = new SendActivateReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register states.
        registerFirstState(receiveActivateRequest);
        registerState(sendActivateReply);
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register transitions.
        receiveActivateRequest.registerDefaultTransition(sendActivateReply);
        
        sendActivateReply.registerTransition(SendActivateReply.AGREE, successEnd);
        sendActivateReply.registerTransition(SendActivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The 'Receive activate request' (single receiver) state.
     * A state in which the 'Activate request' message is received.
     */
    private class ReceiveActivateRequest extends SingleReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-activate-request";
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveActivateRequest() {
            super(NAME, playerAID);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyRole().logInfo("Receiving activate request.");
        }
        
        @Override
        protected int onReceiver() {
            ActivateRequestMessage message = new ActivateRequestMessage();
            boolean messageReceived = receive(message, playerAID);
                
            if (messageReceived) {
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Activate request received.");
        }

        // </editor-fold>
    }

    /**
     * The 'Send activate reply' (multi sender) state.
     * A state in which the 'Activate reply' message is sent.
     */
    private class SendActivateReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-activate-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendActivateReply() {
            super(NAME, playerAID);
        }

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending activate reply.");
        }

        @Override
        protected int onManager() {
            if (getMyRole().isActivable()) {            
                return SendAgreeOrRefuse.AGREE;
            } else {
                return SendAgreeOrRefuse.REFUSE;
            }
        }
        
        @Override
        protected void onAgree() {
            getMyRole().activate();
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Activate reply sent.");
        }
        
        // </editor-fold>
    }

    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Activate role' protocol responder party secceeds.
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
            getMyRole().logInfo("Activate role responder party succeeded.");
        }

        // </editor-fold>
    }

    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Activate role' protocol responder party fails.
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
            getMyRole().logInfo("Activate role responder party failed.");
        }

        // </editor-fold>
    }

    // </editor-fold>
}
