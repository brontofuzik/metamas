package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto_old.ActiveState;
import jadeorg.proto_new.Party;
import jadeorg.proto_old.PassiveState;
import jadeorg.proto_new.Protocol;
import jadeorg.proto_old.State.Event;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;

/**
 * An 'Activate role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-10
 * @version %I% %G%
 */
public class Role_ActivateRoleResponder extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "activate-role-responder";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private AID playerAID;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Role_ActivateRoleResponder(AID playerAID) {
        super(NAME);
        this.playerAID = playerAID;

        registerStatesAndTransitions();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    public Protocol getProtocol() {
        return ActivateRoleProtocol.getInstance();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndTransitions() {
//        // ----- States -----
//        State receiveActivateRequest = new ReceiveActivateRequest();
//        State sendActivateReply = new SendActivateReply();
//        State sendFailure = new SendFailure();
//        State successEnd = new SuccessEnd();
//        State failureEnd = new FailureEnd();
//        // ------------------
//
//        // Register states.
//        registerFirstState(receiveActivateRequest);
//        registerState(sendActivateReply);
//        registerState(sendFailure);
//        registerLastState(successEnd);
//        registerLastState(failureEnd);
//
//        // Register transitions.
//        receiveActivateRequest.registerTransition(Event.SUCCESS ,sendActivateReply);
//        receiveActivateRequest.registerTransition(Event.FAILURE, sendFailure);
//
//        sendActivateReply.registerDefaultTransition(successEnd);
//
//        sendActivateReply.registerDefaultTransition(successEnd);
//
//        sendFailure.registerDefaultTransition(failureEnd);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The 'Receive activate request' (passive) state.
     */
    private class ReceiveActivateRequest extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-activate-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveActivateRequest() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Role)myAgent).logInfo("Receiving activate request.");

            ActivateRequestMessage activateRequestMessage = (ActivateRequestMessage)
                receive(ActivateRequestMessage.class, playerAID);

            if (activateRequestMessage != null) {
                ((Role)myAgent).logInfo("Activate request received.");

                if (isActivable()) {
                    ((Role)myAgent).state = Role.RoleState.ACTIVE;
                    setExitValue(Event.SUCCESS);
                } else {
                    setExitValue(Event.FAILURE);
                }
            } else {
                loop();
            }
        }

        // ---------- PRIVATE ----------

        private boolean isActivable() {
            return ((Role)myAgent).state == Role.RoleState.INACTIVE;
        }

        // </editor-fold>
    }

    /**
     * The 'Send activate reply' (active) state.
     */
    private class SendActivateReply extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-activate-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendActivateReply() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
//            ((Role)myAgent).logInfo("Sending activate reply.");
//
//            // Create the 'Activate reply' JadeOrg message.
//            AgreeMessage activateReplyMessage = new AgreeMessage();
//            activateReplyMessage.addReceiver(playerAID);
//
//            // Send the message.
//            send(AgreeMessage.class, activateReplyMessage);
//            
//            ((Role)myAgent).logInfo("Activate reply sent");
        }

        // </editor-fold>
    }

    /**
     * The 'Send failure' (active) state.
     */
    private class SendFailure extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-failure";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendFailure() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
//            ((Role)myAgent).logInfo("Sending failure.");
//
//            // Create the 'Failure' message.
//            FailureMessage failureMessage = new FailureMessage();
//            failureMessage.addReceiver(playerAID);
//
//            // Send the message.
//            send(FailureMessage.class, failureMessage);
//            
//            ((Role)myAgent).logInfo("Failure sent.");
        }

        // </editor-fold>
    }

    /**
     * The 'Success end' (active) state.
     */
    private class SuccessEnd extends ActiveState {

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
    private class FailureEnd extends ActiveState {

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
