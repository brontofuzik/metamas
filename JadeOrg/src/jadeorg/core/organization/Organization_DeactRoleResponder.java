package jadeorg.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.jadeextensions.State;
import jadeorg.proto.SendAgreeOrRefuse;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A 'Deact role' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Organization_DeactRoleResponder extends ResponderParty {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;
    
    private String roleName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Organization_DeactRoleResponder(ACLMessage aclMessage) {
        super(DeactRoleProtocol.getInstance(), aclMessage);

        playerAID = getACLMessage().getSender();

        buildFSM();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    private Organization getMyOrganization() {
        return (Organization)myAgent;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Registers the transitions and transitions.
     */
    private void buildFSM() {
        State initialize = new Initialize();
        State receiveDeactRequest = new ReceiveDeactRequest();
        State sendDeactReply = new SendDeactReply();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();

        // Register the states.
        registerFirstState(initialize);
        
        registerState(receiveDeactRequest);
        registerState(sendDeactReply);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transisions.
        initialize.registerTransition(Initialize.OK, receiveDeactRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);
        
        receiveDeactRequest.registerDefaultTransition(sendDeactReply);

        sendDeactReply.registerTransition(SendAgreeOrRefuse.AGREE, successEnd);
        sendDeactReply.registerTransition(SendAgreeOrRefuse.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends OneShotBehaviourState {
        
        public static final int OK = 0;
        public static final int FAIL = 1;
        
        private int exitValue;
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyOrganization().logInfo(String.format(
                "Responding to the 'Deact role' protocol (id = %1$s).",
                getACLMessage().getConversationId()));
            exitValue = OK;
        }
        
        @Override
        public int onEnd() {
            return exitValue;
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive deact request' (single receiver) state.
     * A state in which the 'Deact request' message is received.
     */
    private class ReceiveDeactRequest extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            DeactRequestMessage message = new DeactRequestMessage();
            message.parseACLMessage(getACLMessage());
            roleName = message.getRoleName();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send deact reply' (multi sender) state.
     * A state in which the 'Enact reply' message is sent.
     */
    private class SendDeactReply extends SendAgreeOrRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getReceiverAID() {
            return playerAID;
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
    private class SuccessEnd extends OneShotBehaviourState {      
        
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
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyOrganization().logInfo("Deact role responder party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
