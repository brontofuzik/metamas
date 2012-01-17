package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.AssertPreconditions;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRequestMessage;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.SendAgreeOrRefuse;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A 'Deactivate role' protocol responder (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public class Role_DeactivateRoleResponder extends ResponderParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private ACLMessage aclMessage;
    
    private AID playerAID;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Role_DeactivateRoleResponder(ACLMessage aclMessage) {
        super(aclMessage);
        
        this.aclMessage = aclMessage;
        playerAID = aclMessage.getSender();

        buildFSM();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    public Protocol getProtocol() {
        return DeactivateRoleProtocol.getInstance();
    }
    
    // ----- PRIVATE -----
    
    private Role getMyRole() {
        return (Role)myAgent;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void buildFSM() {
        // ----- States -----
        State assertPreconditions = new MyAssertPreconditions();
        State receiveActivateRequest = new ReceiveDeactivateRequest();
        State sendActivateReply = new SendDeactivateReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register states.
        registerFirstState(assertPreconditions);
        
        registerState(receiveActivateRequest);
        registerState(sendActivateReply);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register transitions.
        assertPreconditions.registerTransition(MyAssertPreconditions.SUCCESS, receiveActivateRequest);
        assertPreconditions.registerTransition(MyAssertPreconditions.FAILURE, failureEnd);
        
        receiveActivateRequest.registerDefaultTransition(sendActivateReply);
        
        sendActivateReply.registerTransition(SendDeactivateReply.AGREE, successEnd);
        sendActivateReply.registerTransition(SendDeactivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyAssertPreconditions extends AssertPreconditions {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected boolean preconditionsSatisfied() {
            getMyRole().logInfo(String.format("Responding to the 'Deactivate role' protocol (id = %1$s).",
                aclMessage.getConversationId()));
        
            if (aclMessage.getSender().equals(getMyRole().playerAID)) {
                // The sender player is enacting this role.
                return true;
            } else {
                // The sender player is not enacting this role.
                // TODO
                return false;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive deactivate request' (single receiver) state.
     */
    private class ReceiveDeactivateRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            DeactivateRequestMessage message = new DeactivateRequestMessage();
            message.parseACLMessage(aclMessage);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send deactivate reply' (multi sender) state.
     */
    private class SendDeactivateReply extends SendAgreeOrRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return playerAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending deactivate reply.");
        }
        
        @Override
        protected int onManager() {
            if (getMyRole().isDeactivable()) {            
                return SendAgreeOrRefuse.AGREE;
            } else {
                return SendAgreeOrRefuse.REFUSE;
            }
        }
        
        @Override
        protected void onAgree() {
            getMyRole().deactivate();
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Deactivate reply sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Deactivate role' protocol responder party succeeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyRole().logInfo("Deactivate role responder party succeeded.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Deactivate role' protocol responder party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyRole().logInfo("Deactivate role responder party failed.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
