package jadeorg.core.player;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.SimpleMessage;
import jadeorg.proto_old.ActiveState;
import jadeorg.proto_new.Party;
import jadeorg.proto_old.PassiveState;
import jadeorg.proto_new.Protocol;
import jadeorg.proto_old.State;
import jadeorg.proto_old.State.Event;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;

/**
 * An 'Activate role' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
public class Player_ActivateRoleInitiator extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "activate-role-initiator";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private AID roleAID;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_ActivateRoleInitiator(AID roleAID) {
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
    public Protocol getProtocol() {
        return ActivateRoleProtocol.getInstance();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndtransitions() {
//        // ----- States -----
//        State sendActivateRequest = new SendActivateRequest();
//        State receiveActivateReply = new ReceiveActivateReply();
//        State successEnd = new SuccessEnd();
//        State failureEnd = new FailureEnd();
//        // ------------------
//
//        // Register the states.
//        registerFirstState(sendActivateRequest);
//        registerState(receiveActivateReply);
//        registerLastState(successEnd);
//        registerLastState(failureEnd);
//
//        // Register the transitions (NEW).
//        sendActivateRequest.registerDefaultTransition(receiveActivateReply);
//
//        receiveActivateReply.registerTransition(Event.SUCCESS, successEnd); 
//        receiveActivateReply.registerTransition(Event.FAILURE, failureEnd);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The 'Send activate request' active state.
     * A state in which the 'Activate request' requirementsInformMessage is send.
     */
    private class SendActivateRequest extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-activate-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendActivateRequest() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
//            ((Player)myAgent).logInfo("Sending activate request.");
//
//            ActivateRequestMessage activateRequestMessage = new ActivateRequestMessage();
//            activateRequestMessage.setReceiverRole(roleAID);
//
//            send(ActivateRequestMessage.class, activateRequestMessage);
//
//           ((Player)myAgent).logInfo("Activate request sent.");
        }

        // </editor-fold>
    }

    /**
     * The 'Receive activate reply' passive state.
     * A state in which the 'Activate reply' requirementsInformMessage is received.
     */
    private class ReceiveActivateReply extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Fields">

        private static final String NAME = "receive-activate-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveActivateReply() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">         

        @Override
        public void action() {
            ((Player)myAgent).logInfo("Receiving activate reply.");

            SimpleMessage activateReplyMessage = (SimpleMessage)
                receive(SimpleMessage.class, roleAID);

            if (activateReplyMessage != null) {
                ((Player)myAgent).logInfo("Activate reply received.");

                if (activateReplyMessage.getPerformative() == ACLMessage.AGREE) {
                    // The 'Activate' request was agreed.
                    ((Player)myAgent).knowledgeBase.activateRole(roleAID.getName());
                    setExitValue(Event.SUCCESS);
                } else if (activateReplyMessage.getPerformative() == ACLMessage.REFUSE) {
                    // The 'Activate' request was refused.
                    setExitValue(Event.FAILURE);
                } else {
                    // TODO Send not understood to the role.
                    assert false;
                }
            } else {
                block();
            }
        }

        // </editor-fold>
    }

    /**
     * The 'Success end' active state.
     * A state in which the 'Activate' protocol initiator party ends.
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
            ((Player)myAgent).logInfo("Activate role initiator party succeeded.");
        }

        // </editor-fold>
    }

    /**
     * The 'Failure end' active state.
     * A state in which the 'Activate' protocol initiator party ends.
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
            ((Player)myAgent).logInfo("Activate role initiator party failed.");
        }

        // </editor-fold>
    }

    // </editor-fold>
}
