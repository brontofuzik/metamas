package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto_old.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto_old.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;

/**
 * A 'Deactivate role' protocol initiator. 
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
public class zzzPlayer_DeactivateRoleInitiator extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "deactivate-protocol-initiator";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private AID roleAID;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public zzzPlayer_DeactivateRoleInitiator(AID roleAID) {
        super(NAME);
        // ----- Preconditions -----
        assert roleAID != null;
        // -------------------------

        this.roleAID = roleAID;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    @Override
    public Protocol getProtocol() {
        return DeactivateRoleProtocol.getInstance();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndtransitions() {
//        // ----- States -----
//        State sendDeactivateRequest = new SendDeactivateRequest();
//        State receiveActivateReply = new ReceiveDeactivateReply();
//        State end = new End();
//        // ------------------
//
//        // Register the states.
//        registerFirstState(sendDeactivateRequest);
//        registerState(receiveActivateReply);
//        registerLastState(end);
//
//        // Register the transitions (OLD).
//        registerDefaultTransition(sendDeactivateRequest, receiveActivateReply);
//
//        registerDefaultTransition(receiveActivateReply, end);
//
////            // Register the transitions (NEW).
////            sendDeactivateRequest.registerDefaultTransition(receiveDeactivateReply);
////            
////            receiveDeactivateReply.registerDefaultTransition(successEnd);  
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The 'Send deactivate request' active state.
     * A state in which the 'Deactivate request' requirementsInformMessage is send.
     */
    private class SendDeactivateRequest extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-deactivate-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendDeactivateRequest()  {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
//            DeactivateRequestMessage deactivateRequestMessage = new DeactivateRequestMessage();
//            deactivateRequestMessage.setReceiverRole(roleAID);
//
//            send(DeactivateRequestMessage.class, deactivateRequestMessage);
        }

        // </editor-fold>
    }

    /**
     * The 'Receive deactivate reply' passive state.
     * A state in which the 'Deactivate reply' requirementsInformMessage is received.
     */
    private class ReceiveDeactivateReply extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-deactivate-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveDeactivateReply() {
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

    /**
     * The 'SuccessEnd' active state.
     * A state in which the 'Deactivate' protocol initiator party ends.
     */
    private class End extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "end";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        End() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
        }

        // </editor-fold>
    }

    // </editor-fold>    
}
