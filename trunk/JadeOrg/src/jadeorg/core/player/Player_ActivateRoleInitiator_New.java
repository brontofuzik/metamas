package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.SimpleState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.ReceiveAgreeOrRefuse;

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

    private String roleName;
    
    private AID roleAID;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_ActivateRoleInitiator_New(String roleName, AID roleAID) {
        super(NAME);
        // ----- Preconditions -----
        assert roleAID != null;
        // -------------------------

        this.roleName = roleName;
        this.roleAID = roleAID;
        registerStatesAndtransitions();
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    @Override
    public Protocol getProtocol() {
        return ActivateRoleProtocol.getInstance();
    }
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndtransitions() {
        // ----- States -----
        State sendActivateRequest = new SendActivateRequest();
        State receiveActivateReply = new ReceiveActivateReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
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
     * The 'Send activate request' (single sender) state.
     * A state in which the 'Activate request' message is sent.
     */
    private class SendActivateRequest extends SingleSenderState {

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
        protected void onEntry() {
            getMyPlayer().logInfo("Sending activate request.");
        }
        
        @Override
        protected void onSingleSender() {
            ActivateRequestMessage activateRequestMessage = new ActivateRequestMessage();

            send(activateRequestMessage, roleAID);            
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Activate request sent.");
        }

        // </editor-fold>
    }
    
    /**
     * The 'Receive activate reply' (multi sender) state.
     * A state in which the 'Activate reply' message is received.
     */
    private class ReceiveActivateReply extends ReceiveAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-activate-reply";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveActivateReply() {
            super(NAME, roleAID);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving activate reply.");
        }
        
        @Override
        protected void onAgree() {
            getMyPlayer().knowledgeBase.activateRole(roleName);
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Activate reply received.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Activate role' protocol initiator party succeeds.
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
            getMyPlayer().logInfo("Activate role initiator party succeeded.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Activate role' protocol initiator party fails.
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
            getMyPlayer().logInfo("Activate role initiator party failed.");
        }

        // </editor-fold>
    }
        
    // </editor-fold>
}
