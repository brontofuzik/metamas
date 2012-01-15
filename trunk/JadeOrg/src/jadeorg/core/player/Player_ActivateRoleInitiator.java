package jadeorg.core.player;

import jade.core.AID;
import jadeorg.proto.AssertPreconditions;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.ReceiveAgreeOrRefuse;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * An 'Activate role' protocol initiator party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
public class Player_ActivateRoleInitiator extends InitiatorParty {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The role name. */
    private String roleName;
    
    /** The role AID. */
    private AID roleAID;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player_ActivateRoleInitiator(String roleName) {
        // ----- Preconditions -----
        assert roleAID != null;
        // -------------------------

        this.roleName = roleName;
        
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
        State assertPreconditions = new MyAssertPreconditions();
        State sendActivateRequest = new SendActivateRequest();
        State receiveActivateReply = new ReceiveActivateReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register the states.
        registerFirstState(assertPreconditions);
        
        registerState(sendActivateRequest);
        registerState(receiveActivateReply);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register the transitions.
        assertPreconditions.registerTransition(MyAssertPreconditions.SUCCESS, sendActivateRequest);
        assertPreconditions.registerTransition(MyAssertPreconditions.FAILURE, failureEnd);
        
        sendActivateRequest.registerDefaultTransition(receiveActivateReply);

        receiveActivateReply.registerTransition(ReceiveActivateReply.AGREE, successEnd); 
        receiveActivateReply.registerTransition(ReceiveActivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyAssertPreconditions extends AssertPreconditions {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected boolean preconditionsSatisfied() {
            getMyPlayer().logInfo(String.format("Initiating the 'Activate role' (%1$s) protocol.",
                roleName));

            // Check if the role can be activated.
            if (getMyPlayer().knowledgeBase.canActivateRole(roleName)) {
                // The role can be activated.
                roleAID = getMyPlayer().knowledgeBase.getEnactedRole(roleName).getRoleAID();
                return true;
            } else {
                // The role can not be activated.
                String message = String.format("Error activating the role '%1$s'. It is not enacted.",
                    roleName);
                return false;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send activate request' (single sender) state.
     * A state in which the 'Activate request' message is sent.
     */
    private class SendActivateRequest extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return roleAID;
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

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return roleAID;
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
    private class SuccessEnd extends OneShotBehaviourState {

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
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyPlayer().logInfo("Activate role initiator party failed.");
        }

        // </editor-fold>
    }
        
    // </editor-fold>
}
