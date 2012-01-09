package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.Party;
import jadeorg.proto.Protocol;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRequestMessage;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.SendAgreeOrRefuse;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A 'Deactivate role' protocol responder (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public class Role_DeactivateRoleResponder extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "deactivate-role-responder";

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private ACLMessage aclMessage;
    
    private AID playerAID;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    Role_DeactivateRoleResponder(ACLMessage aclMessage) {
        super(NAME);
        // ----- Preconditions -----
        assert aclMessage != null;
        // -------------------------
        
        this.aclMessage = aclMessage;
        setProtocolId(aclMessage.getConversationId());
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
        State receiveActivateRequest = new ReceiveDeactivateRequest();
        State sendActivateReply = new SendDeactivateReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------

        // Register states.
        registerFirstState(receiveActivateRequest);
        registerState(sendActivateReply);
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register transitions.
        receiveActivateRequest.registerDefaultTransition(sendActivateReply);
        
        sendActivateReply.registerTransition(SendDeactivateReply.AGREE, successEnd);
        sendActivateReply.registerTransition(SendDeactivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Receive deactivate request' (single receiver) state.
     */
    private class ReceiveDeactivateRequest extends OneShotBehaviourState {
        
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
            DeactivateRequestMessage message = new DeactivateRequestMessage();
            message.parseContent(aclMessage.getContent());
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send deactivate reply' (multi sender) state.
     */
    private class SendDeactivateReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-deactivate-reply";

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">

        SendDeactivateReply() {
            super(NAME, playerAID);
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
            getMyRole().logInfo("Deactivate role responder party succeeded.");
        }

        // </editor-fold>
    }
        
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Deactivate role' protocol responder party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

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
            getMyRole().logInfo("Deactivate role responder party failed.");
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
