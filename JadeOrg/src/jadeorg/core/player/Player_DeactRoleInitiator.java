package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.ReceiveAgreeOrRefuse;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A 'Deact role' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Player_DeactRoleInitiator extends InitiatorParty {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The organization AID */
    private AID organizationAID;

    /** The role name */
    private String roleName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_DeactRoleInitiator(AID organization, String roleName) {
        // ----- Preconditions -----
        assert organization != null;
        assert roleName != null && !roleName.isEmpty();
        // -------------------------

        this.organizationAID = organization;
        this.roleName = roleName;

        buildFSM();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    @Override
    public Protocol getProtocol() {
        return DeactRoleProtocol.getInstance();
    }
    
    // ----- PRIVATE -----
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State sendDeactRequest = new SendDeactRequest();
        State receiveDeactReply = new ReceiveDeactReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(sendDeactRequest);
        registerState(receiveDeactReply);
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions (NEW).
        sendDeactRequest.registerDefaultTransition(receiveDeactReply);
            
        receiveDeactReply.registerTransition(ReceiveDeactReply.AGREE, successEnd);
        receiveDeactReply.registerTransition(ReceiveDeactReply.REFUSE, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Send deact request' (single sender) state.
     * A state in which the 'Deact request' message is sent.
     */
    private class SendDeactRequest extends SingleSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending deact request.");
        }
        
        @Override
        protected void onSingleSender() {
            DeactRequestMessage message = new DeactRequestMessage();
            message.setRoleName(roleName);

            send(message, organizationAID);
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Deact request sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive deact reply' (multi receiver) state.
     * A state in which the 'Deact reply' message is received.
     */
    private class ReceiveDeactReply extends ReceiveAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveDeactReply() {
            super(organizationAID);
        }

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving deact reply.");
        }
        
        @Override
        protected void onAgree() {
            getMyPlayer().knowledgeBase.deactRole(roleName);
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Deact reply received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Deact role' initiator party succeedes.
     */
    private class SuccessEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("Deact role initiator party succeeded.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Deact role' initiator party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyPlayer().logInfo("Deact role initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
