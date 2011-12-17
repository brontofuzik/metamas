package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.simplemessages.AgreeMessage;
import jadeorg.lang.simplemessages.RefuseMessage;
import jadeorg.lang.simplemessages.SimpleMessage;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto_new.jadeextensions.State;
import jadeorg.proto_new.MultiReceiverState;
import jadeorg.proto_new.MultiSenderState;
import jadeorg.proto_new.SimpleState;

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
        this.playerAID = playerAID;

        registerStatesAndTransitions();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    protected Protocol getProtocol() {
        return ActivateRoleProtocol.getInstance();
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
     * The 'Receive activate request' (passive) state.
     */
    private class ReceiveActivateRequest extends MultiReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-activate-request";

        private static final int RECEIVER1 = 1;
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveActivateRequest() {
            super(NAME);
            
            addReceiver(new ReceiveActivateRequest_Receiver(0));
            buildFSM();
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Role)myAgent).logInfo("Receiving activate request.");
        }

        @Override
        protected void onExit() {
            ((Role)myAgent).logInfo("Activate request received.");
        }

        // </editor-fold>
    
        // <editor-fold defaultstate="collapsed" desc="Classes">
        
        private class ReceiveActivateRequest_Receiver extends InnerReceiverState {

            // <editor-fold defaultstate="collapsed" desc="Fields">
            
            private static final String NAME = "receiver";
            
            // </editor-fold>
            
            // <editor-fold defaultstate="collapsed" desc="Constructors">
            
            ReceiveActivateRequest_Receiver(int outerReceiverStateExitValue) {
                super(NAME, outerReceiverStateExitValue);
            }
            
            // </editor-fold>
            
            @Override
            public void action() {
                ActivateRequestMessage activateRequestMessage = (ActivateRequestMessage)
                    receive(ActivateRequestMessage.class, playerAID);
            }
        } 
        
        // </editor-fold>
    }

    /**
     * The 'Send activate reply' (active) state.
     */
    private class SendActivateReply extends MultiSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        // ----- Exit values -----
        static final int AGREE = 1;
        static final int REFUSE = 2;
        // -----------------------
        
        private static final String NAME = "send-activate-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendActivateReply() {
            super(NAME);
            
            addSender(AGREE, this.new SendAgree(playerAID));
            addSender(REFUSE, this.new SendRefuse(playerAID));
            buildFSM();
        }

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Role)myAgent).logInfo("Sending activate reply.");
        }

        @Override
        protected int onManager() {
            if (isActivable()) {
                ((Role)myAgent).state = Role.RoleState.ACTIVE;
                return AGREE;
            } else {
                return REFUSE;
            }
        }

        @Override
        protected void onExit() {
            ((Role)myAgent).logInfo("Activate reply sent");
        }
        
        // ---------- PRIVATE ----------
        
        private boolean isActivable() {
            return ((Role)myAgent).state == Role.RoleState.INACTIVE;
        }
        
        // </editor-fold>
    }

    /**
     * The 'Success end' (active) state.
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
            ((Role)myAgent).logInfo("Activate role initiator protocol succeeded.");
        }

        // </editor-fold>
    }

    /**
     * The 'Failure end' (active) state.
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
            ((Role)myAgent).logInfo("Activate role initiator protocol failed.");
        }

        // </editor-fold>
    }

    // </editor-fold>
}
