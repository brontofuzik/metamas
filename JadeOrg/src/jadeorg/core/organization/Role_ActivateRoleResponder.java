package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.AssertPreconditions;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.SendAgreeOrRefuse;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * An 'Activate role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-10
 * @version %I% %G%
 */
public class Role_ActivateRoleResponder extends ResponderParty {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private ACLMessage aclMessage;
    
    private AID playerAID;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Role_ActivateRoleResponder(ACLMessage aclMessage) {
        super(aclMessage);
        
        this.aclMessage = aclMessage;
        playerAID = aclMessage.getSender();
        
        buildFSM();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    public Protocol getProtocol() {
        return ActivateRoleProtocol.getInstance();
    }
    
    private Role getMyRole() {
        return (Role)myAgent;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void buildFSM() {
        // ----- States -----
        State assertPreconditions = new MyAssertPreconditions();
        State receiveActivateRequest = new ReceiveActivateRequest();
        State sendActivateReply = new SendActivateReply();
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
        
        sendActivateReply.registerTransition(SendActivateReply.AGREE, successEnd);
        sendActivateReply.registerTransition(SendActivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    private class MyAssertPreconditions extends AssertPreconditions {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected boolean preconditionsSatisfied() {
            getMyRole().logInfo(String.format("Responding to the 'Activate role' protocol (id = %1$s).",
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
     * The 'Receive activate request' (single receiver) state.
     * A state in which the 'Activate request' message is received.
     */
    private class ReceiveActivateRequest extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
       
        @Override
        public void action() {
            ActivateRequestMessage message = new ActivateRequestMessage();
            message.parseACLMessage(aclMessage);
        }

        // </editor-fold>
    }

    /**
     * The 'Send activate reply' (multi sender) state.
     * A state in which the 'Activate reply' message is sent.
     */
    private class SendActivateReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return playerAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending activate reply.");
        }

        @Override
        protected int onManager() {
            if (getMyRole().isActivable()) {            
                return SendAgreeOrRefuse.AGREE;
            } else {
                return SendAgreeOrRefuse.REFUSE;
            }
        }
        
        @Override
        protected void onAgree() {
            getMyRole().activate();
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Activate reply sent.");
        }
        
        // </editor-fold>
    }

    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Activate role' protocol responder party secceeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyRole().logInfo("Activate role responder party succeeded.");
        }

        // </editor-fold>
    }

    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Activate role' protocol responder party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyRole().logInfo("Activate role responder party failed.");
        }

        // </editor-fold>
    }

    // </editor-fold>
}
