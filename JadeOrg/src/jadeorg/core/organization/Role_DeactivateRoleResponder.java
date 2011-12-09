package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto.State;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateReplyMessage;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;

/**
 * A 'Deactivate role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-10
 * @version %I% %G%
 */
public class Role_DeactivateRoleResponder extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "deactivate-role-responder";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private AID playerAID;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Role_DeactivateRoleResponder(AID playerAID) {
        super(NAME);
        this.playerAID = playerAID;

        registerStatesAndTransitions();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    protected Protocol getProtocol() {
        return DeactivateRoleProtocol.getInstance();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndTransitions() {
        // ----- States -----
        State receiveDeactivateRequest = new ReceiveDeactivateRequest();
        State sendDeactivateReply = new SendDeactivateReply();
        State end = new End();
        // ------------------

        // Register states.
        registerFirstState(receiveDeactivateRequest);
        registerState(sendDeactivateReply);
        registerLastState(end);

        // Register transitions.
        receiveDeactivateRequest.registerDefaultTransition(sendDeactivateReply);
        sendDeactivateReply.registerDefaultTransition(end);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The 'Receive deactivate request' (passive) state.
     */
    private class ReceiveDeactivateRequest extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "recive-deactivate-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveDeactivateRequest() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
        }

        // </editor-fold>
    }

    /**
     * The 'Send deactivate reply' (active) state.
     */
    private class SendDeactivateReply extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Fields">

        private static final String NAME = "send-deactivate-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendDeactivateReply() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            DeactivateReplyMessage deactivateReplyMessage = new DeactivateReplyMessage();      
            if (isDeactivable()) {
                deactivateReplyMessage.setAgree(true);
                ((Role)myAgent).state = Role.RoleState.INACTIVE;
            } else {
                deactivateReplyMessage.setAgree(false);
            }
            deactivateReplyMessage.addReceiver(playerAID);

            send(DeactivateReplyMessage.class, deactivateReplyMessage);
        }

        private boolean isDeactivable() {
            return ((Role)myAgent).state == Role.RoleState.ACTIVE;
        }

        // </editor-fold>  
    }

    /**
     * The 'SuccessEnd' (active) state.
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
            // Do nothing.
        }

        // </editor-fold>
    }

    // </editor-fold>
}
