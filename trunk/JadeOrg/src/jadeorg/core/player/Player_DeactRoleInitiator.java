package jadeorg.core.player;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.SimpleMessage;
import jadeorg.proto_old.ActiveState;
import jadeorg.proto.Party;
import jadeorg.proto_old.PassiveState;
import jadeorg.proto.Protocol;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;

/**
 * A 'Deact role' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
public class Player_DeactRoleInitiator extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "deact-protocol-initiator";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The organization AID */
    private AID organizationAID;

    /** The role name */
    private String roleName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_DeactRoleInitiator(AID organization, String roleName) {
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
    public Protocol getProtocol() {
        return DeactRoleProtocol.getInstance();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndTransitions() {
//        // ----- States -----
//        State sendDeactRequest = new SendDeactRequest();
//        State receiveDeactReply = new ReceiveDeactReply();
//        State end = new End();
//        // ------------------
//
//        // Register the states.
//        registerFirstState(sendDeactRequest);
//        registerState(receiveDeactReply);
//        registerLastState(end);
//
//        // Register the transitions (OLD).
//        registerDefaultTransition(sendDeactRequest, receiveDeactReply);
//
//        registerDefaultTransition(receiveDeactReply, end);
//
//            // Register the transitions (NEW).
//            sendDeactRequest.registerDefaultTransition(receiveDeactReply);
//            
//            receiveDeactReply.registerDefaultTransition(successEnd);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The 'Send deact request' active state.
     * A state in which the 'Deact request' requirementsInformMessage is send.
     */
    private class SendDeactRequest extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-deact-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendDeactRequest() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
//            DeactRequestMessage message = new DeactRequestMessage();
//            message.setReceiverOrganization(organizationAID);
//            message.setRoleName(roleName);
//
//            send(DeactRequestMessage.class, message);
        }

        // </editor-fold>
    }

    /**
     * The 'Receive deact reply' passive state.
     * A state in which the 'Deact reply' requirementsInformMessage is send.
     */
    private class ReceiveDeactReply extends PassiveState {

        // <editor-fold defaultstate="collapsed" desc="Fields">

        private static final String NAME = "receive-deact-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveDeactReply() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            SimpleMessage aclMessageWrapper = (SimpleMessage)receive(SimpleMessage.class, organizationAID);            
            if (aclMessageWrapper != null) {
                if (aclMessageWrapper.getPerformative() == ACLMessage.AGREE) {
                    // The request was agreed.
                    ((Player)myAgent).knowledgeBase.deactRole(roleName);
                } else if (aclMessageWrapper.getPerformative() == ACLMessage.REFUSE) {
                    // The request was refused.
                } else {
                    // TODO Send not understood.
                    assert false;
                }
            } else {
                block();
            }
        }

        // </editor-fold>
    }

    /**
     * The 'SuccessEnd' active state.
     * A state in which the 'Deact' initiator party ends.
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
            // TODO Implement.
            throw new UnsupportedOperationException("Not supported yet.");
        }

        // </editor-fold>
    }

    // </editor-fold>    
}
