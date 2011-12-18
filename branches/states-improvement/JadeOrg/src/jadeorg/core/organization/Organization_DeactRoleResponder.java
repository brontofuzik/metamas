package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto.State;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;

/**
 * A 'Deact role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
public class Organization_DeactRoleResponder extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "deact-role-responder";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String roleName;

    private AID player;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Organization_DeactRoleResponder(String roleName, AID player) {
        super(NAME);
        // ----- Preconditions -----
        assert !roleName.isEmpty();
        assert player != null;
        // -------------------------

        this.roleName = roleName;
        this.player = player;

        registerStatesAndTransitions();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    protected Protocol getProtocol() {
        return DeactRoleProtocol.getInstance();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Registers the transitions and transitions.
     */
    private void registerStatesAndTransitions() {
//        State receiveDeactRequest = new ReceiveDeactRequest();
//        State sendDeactInformation = new SendDeactInformation();
//        State sendDeactFailure = new SendDeactFailure();
//        State end = new End();
//
//        // Register the states.
//        registerFirstState(receiveDeactRequest);
//        registerState(sendDeactInformation);
//        registerState(sendDeactFailure);
//        registerLastState(end);
//
//        // Register the transisions (OLD).
//        registerTransition(receiveDeactRequest, sendDeactInformation, PassiveState.Event.SUCCESS);
//        registerTransition(receiveDeactRequest, sendDeactFailure, PassiveState.Event.FAILURE);
//
//        registerDefaultTransition(sendDeactInformation, end);
//
//        registerDefaultTransition(sendDeactFailure, end);
//
////            // Register the transisions (NEW).
////            receiveDeactRequest.registerTransition(PassiveState.Event.SUCCESS, sendDeactInformation);
////            receiveDeactRequest.registerTransition(PassiveState.Event.FAILURE, sendDeactFailure);
////            
////            sendDeactInformation.registerDefaultTransition(end);
////            
////            sendDeactFailure.registerDefaultTransition(end);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">
    /**
     * The 'Receive deact request' active state.
     */
    class ReceiveDeactRequest extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-deact-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveDeactRequest() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ((Organization)myAgent).logInfo("Receiving deact request.");

            DeactRequestMessage deactRequestMessage = (DeactRequestMessage)
                receive(DeactRequestMessage.class, null);
            if (deactRequestMessage != null) {
                roleName = deactRequestMessage.getRoleName();

                if (((Organization)myAgent).roles.containsKey(roleName)) {
                    // The role is defined for this organization.
                    if (((Organization)myAgent).knowledgeBase.isRoleEnactedByPlayer(roleName, player)) {
                        // The is enacted by the player.
                    } else {
                        // The role is not enacted by the player.
                    }
                } else {
                    // The role is not defined for this organization.
                }
            } else {
                loop();
            }
        }

        // </editor-fold>
    }

    /**
     * The 'Send deact information' active state.
     */
    class SendDeactInformation extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-deact-information";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendDeactInformation() {
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
     * The 'Send deact failure' active state.
     */
    class SendDeactFailure extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-deact-failure";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendDeactFailure() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
//            // Create the 'Failure' message.
//            FailureMessage failureMessage = new FailureMessage();
//            failureMessage.setReceiverPlayer(player);
//
//            send(FailureMessage.class, failureMessage);
        }

        // </editor-fold>
    }

    /**
     * The 'End' active state.
     */
    class End extends ActiveState {

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
