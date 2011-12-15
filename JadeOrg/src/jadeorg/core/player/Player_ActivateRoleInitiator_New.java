package jadeorg.core.player;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.core.player.Player;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto_new.MultiReceiverState;
import jadeorg.proto_new.MultiSenderState;
import jadeorg.proto_new.SimpleState;

/**
 * An 'Activate role' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
public class Player_ActivateRoleInitiator_New extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "activate-role-initiator-new";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private AID roleAID;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_ActivateRoleInitiator_New(AID roleAID) {
        super(NAME);
        // ----- Preconditions -----
        assert roleAID != null;
        // -------------------------

        this.roleAID = roleAID;

        registerStatesAndtransitions();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    @Override
    protected Protocol getProtocol() {
        return ActivateRoleProtocol.getInstance();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndtransitions() {
        // ----- States -----
        jadeorg.proto_new.jadeextensions.State sendActivateRequest = new SendActivateRequest();
        jadeorg.proto_new.jadeextensions.State receiveActivateReply = new ReceiveActivateReply();
        jadeorg.proto_new.jadeextensions.State successEnd = new SuccessEnd();
        jadeorg.proto_new.jadeextensions.State failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(sendActivateRequest);
        registerState(receiveActivateReply);
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions.
        sendActivateRequest.registerDefaultTransition(receiveActivateReply);

        receiveActivateReply.registerTransition(ReceiveActivateReply.AGREE, successEnd); 
        receiveActivateReply.registerTransition(ReceiveActivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
        
    /**
     * The 'Send activate request' active state.
     * A state in which the 'Activate request' requirementsInformMessage is send.
     */
    private class SendActivateRequest extends MultiSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-activate-request";

        private static final int SENDER1 = 1;

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendActivateRequest() {
            super(NAME);

            addSender(SENDER1, new SendActivateRequest_Sender());
            buildFSM();
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Player)myAgent).logInfo("Sending activate request.");
        }

        @Override
        protected int onManager() {
            return SENDER1;
        }

        @Override
        protected void onExit() {
            ((Player)myAgent).logInfo("Activate request sent.");
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Classes">

        private class SendActivateRequest_Sender extends InnerSenderState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">

            private static final String NAME = "sender";

            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Constructors">

            SendActivateRequest_Sender() {
                super(NAME);
            }

            // </editor-fold>

            @Override
            public void action() {
                ActivateRequestMessage activateRequestMessage = new ActivateRequestMessage();
                activateRequestMessage.setReceiverRole(roleAID);

                send(ActivateRequestMessage.class, activateRequestMessage);                  
            }
        }

        // </editor-fold>
    }
        
    private class ReceiveActivateReply extends MultiReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        // ----- Exit values -----
        static final int AGREE = 0;
        static final int REFUSE = 1;
        // -----------------------
        
        private static final String NAME = "receive-activate-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveActivateReply() {
            super(NAME);

            addReceiver(ACLMessage.AGREE, this.new ReceiveAgree());
            addReceiver(ACLMessage.REFUSE, this.new ReceiveRefuse());
            buildFSM();
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            ((Player)myAgent).logInfo("Receiving activate reply.");
        }

        @Override
        protected void onExit() {
            ((Player)myAgent).logInfo("Activate reply received.");
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Classes">     

        private class ReceiveAgree extends InnerReceiverState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">

            private static final String NAME = "receive-agree";

              // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Constructors">

            ReceiveAgree() {
                super(NAME);
            }

            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Methods">

            @Override
            public void action() {
                setExitValue(AGREE);
            }

            // </editor-fold>
        }

        private class ReceiveRefuse extends InnerReceiverState {

            // <editor-fold defaultstate="collapsed" desc="Constant fields">

            private static final String NAME = "receive-refuse";

            // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Constructors">

            ReceiveRefuse() {
                super(NAME);
            }

           // </editor-fold>

            // <editor-fold defaultstate="collapsed" desc="Methods">

            @Override
            public void action() {
                setExitValue(REFUSE);
            }

            // </editor-fold>
        }

        // </editor-fold>
    }
        
    /**
     * The 'Success end' active state.
     * A state in which the 'Activate' protocol initiator party ends.
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
            ((Player)myAgent).logInfo("Activate role initiator party succeeded.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Failure end' active state.
     * A state in which the 'Activate' protocol initiator party ends.
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
            ((Player)myAgent).logInfo("Activate role initiator party failed.");
        }

        // </editor-fold>
    }
        
    // </editor-fold>
}
