package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import thespian4jade.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import thespian4jade.proto.jadeextensions.State;
import thespian4jade.proto.SendAgreeOrRefuse;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;

/**
 * A 'Deact role' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Organization_DeactRole_ResponderParty extends ResponderParty<Organization> {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;
    
    private String roleName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Organization_DeactRole_ResponderParty(ACLMessage aclMessage) {
        super(DeactRoleProtocol.getInstance(), aclMessage);

        playerAID = getACLMessage().getSender();

        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        State initialize = new MyInitialize();
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
        initialize.registerTransition(MyInitialize.OK, receiveDeactRequest);
        initialize.registerTransition(MyInitialize.FAIL, failureEnd);
        
        receiveDeactRequest.registerDefaultTransition(sendDeactReply);

        sendDeactReply.registerTransition(SendAgreeOrRefuse.AGREE, successEnd);
        sendDeactReply.registerTransition(SendAgreeOrRefuse.REFUSE, failureEnd);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyInitialize extends Initialize {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int initialize() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deact role' protocol (id = %1$s) responder party started.",
                getACLMessage().getConversationId()));
            
            return OK;
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
        protected AID[] getReceivers() {
            return new AID[] { playerAID };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending deact reply.");
        }
        
        @Override
        protected int onManager() {
            if (getMyAgent().roles.containsKey(roleName)) {
                // The role is defined for this organization.
                if (getMyAgent().knowledgeBase.isRoleEnactedByPlayer(roleName, playerAID)) {
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
            getMyAgent().knowledgeBase
                .updateRoleIsDeacted(roleName, playerAID);
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deact reply sent.");
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
            // Raise the 'Role deacted' event.
            getMyOrganization().raiseEvent("role-deacted", roleName);
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deact role' protocol (id = %1$s) responder party succeeded.",
                getProtocolId()));
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
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deact role' protocol (id = %1$s) responder party failed.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
