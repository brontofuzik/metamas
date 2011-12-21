package jadeorg.core.organization;

import jade.core.AID;
import jadeorg.proto_new.Party;
import jadeorg.proto_new.Protocol;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto_new.SimpleState;
import jadeorg.proto_new.SingleReceiverState;
import jadeorg.proto_new.jadeextensions.State;
import jadeorg.proto_new.jadeorgextensions.SendAgreeOrRefuse;

/**
 * A 'Deact role' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Organization_DeactRoleResponder_New extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">

    private static final String NAME = "deact-role-responder-new";

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String roleName;

    private AID playerAID;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Organization_DeactRoleResponder_New(AID playerAID) {
        super(NAME);
        // ----- Preconditions -----
        assert !roleName.isEmpty();
        assert playerAID != null;
        // -------------------------

        this.playerAID = playerAID;

        registerStatesAndTransitions();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    public Protocol getProtocol() {
        return DeactRoleProtocol.getInstance();
    }
    
    // ----- PRIVATE -----
    
    private Organization getMyOrganization() {
        return (Organization)myAgent;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Registers the transitions and transitions.
     */
    private void registerStatesAndTransitions() {
        State receiveDeactRequest = new ReceiveDeactRequest();
        State sendDeactReply = new SendDeactReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();

        // Register the states.
        registerFirstState(receiveDeactRequest);
        registerState(sendDeactReply);
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transisions.
        receiveDeactRequest.registerDefaultTransition(sendDeactReply);

        sendDeactReply.registerTransition(SendAgreeOrRefuse.AGREE, successEnd);
        sendDeactReply.registerTransition(SendAgreeOrRefuse.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Receive deact request' (single receiver) state.
     * A state in which the 'Deact request' message is received.
     */
    private class ReceiveDeactRequest extends SingleReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-deact-request";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        ReceiveDeactRequest() {
            super(NAME, playerAID);
        }

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyOrganization().logInfo("Receiving deact request.");
        }
        
        @Override
        protected int onReceiver() {
            DeactRequestMessage message = new DeactRequestMessage();
            boolean messageReceived = receive(message, playerAID);
            
            if (messageReceived) {
                roleName = message.getRoleName();
                return InnerReceiverState.RECEIVED;
            } else {
                return InnerReceiverState.NOT_RECEIVED;
            }
        }
        
        @Override
        protected void onExit() {
            getMyOrganization().logInfo("Deact request received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send deact reply' (multi sender) state.
     * A state in which the 'Enact reply' message is sent.
     */
    private class SendDeactReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-deact-reply";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendDeactReply() {
            super(NAME, playerAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyOrganization().logInfo("Sending deact reply.");
        }
        
        @Override
        protected int onManager() {
            if (getMyOrganization().roles.containsKey(roleName)) {
                // The role is defined for this organization.
                if (getMyOrganization().knowledgeBase.isRoleEnactedByPlayer(roleName, playerAID)) {
                    // The is enacted by the player.
                    return SendAgreeOrRefuse.AGREE;
                } else {
                    // The role is not enacted by the player.
                    return SendAgreeOrRefuse.REFUSE;
                }
            } else {
                // The role is not defined for this organization.
                return SendAgreeOrRefuse.REFUSE;
            }
        }
        
        @Override
        protected void onAgree() {
            getMyOrganization().knowledgeBase.updateRoleIsDeacted(roleName, playerAID);
        }

        @Override
        protected void onExit() {
            getMyOrganization().logInfo("Deact reply sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (simple) state.
     * A state in which the 'Deact role' responder party succeedes.
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
            getMyOrganization().logInfo("Deact role responder party succeeded.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Failure end' (simple) state.
     * A state in which the 'Deact role' responder party fails.
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
            getMyOrganization().logInfo("Deact role responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
