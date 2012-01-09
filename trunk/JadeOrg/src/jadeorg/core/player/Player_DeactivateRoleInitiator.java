package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRequestMessage;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.ReceiveAgreeOrRefuse;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A 'Deactivate role' protocol initiator (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public class Player_DeactivateRoleInitiator extends InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String roleName;
    
    private AID roleAID;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_DeactivateRoleInitiator(String roleName, AID roleAID) {
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
        return DeactivateRoleProtocol.getInstance();
    }
    
    // ----- PRIVATE -----
    
    private Player getMyPlayer() {
        return (Player)myAgent;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void registerStatesAndtransitions() {
        // ----- States -----
        State sendDeactivateRequest = new SendDeactivateRequest();
        State receiveDeactivateReply = new ReceiveDeactivateReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(sendDeactivateRequest);
        registerState(receiveDeactivateReply);
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions.
        sendDeactivateRequest.registerDefaultTransition(receiveDeactivateReply);

        receiveDeactivateReply.registerTransition(ReceiveDeactivateReply.AGREE, successEnd); 
        receiveDeactivateReply.registerTransition(ReceiveDeactivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * 
     */
    private class SendDeactivateRequest extends SingleSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Sending deactivate request.");
        }
        
        @Override
        protected void onSingleSender() {
            DeactivateRequestMessage deactivateRequestMessage = new DeactivateRequestMessage();

            send(deactivateRequestMessage, roleAID);   
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Deactivate request sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * 
     */
    private class ReceiveDeactivateReply extends ReceiveAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveDeactivateReply() {
            super(roleAID);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyPlayer().logInfo("Receiving deactivate reply.");
        }
        
        @Override
        protected void onAgree() {
            getMyPlayer().knowledgeBase.deactivateRole();
        }

        @Override
        protected void onExit() {
            getMyPlayer().logInfo("Deactivate reply received.");
        }    
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Deactivate role' protocol initiator party succeeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyPlayer().logInfo("Deactivate role initiator party succeeded.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Deactivate role' protocol initiator party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyPlayer().logInfo("Deactivate role initiator party failed.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
