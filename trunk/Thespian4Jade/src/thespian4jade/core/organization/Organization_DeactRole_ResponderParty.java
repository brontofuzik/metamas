package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import thespian4jade.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import thespian4jade.proto.jadeextensions.IState;
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
        IState initialize = new MyInitialize();
        IState receiveDeactRequest = new ReceiveDeactRequest();
        IState sendDeactReply = new SendDeactReply();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();

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
                System.out.println("----- ROLE: " + roleName + " -----");
                System.out.println("----- PLAYER: " + playerAID + " -----");
                if (getMyAgent().knowledgeBase.isRoleEnactedByPlayer(roleName, playerAID)) {
                    System.out.println("----- AGREE -----");
                    // The is enacted by the player.
                    return SendAgreeOrRefuse.AGREE;
                } else {
                    System.out.println("----- REFUSE -----");
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
            // Update the knowledge base.
            getMyAgent().knowledgeBase
                .updateRoleIsDeacted(roleName, playerAID);
            
            // Stop the role agent.
            // TODO (priority: medium) Implement.
            
            // Unlink the position from its organization.
            // TODO (priority: medium) Implement.
            
            // Destroy the role agent.
            // TODO (priority: medium) Implement.
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
            // Publish the 'Role deacted' event.
            getMyAgent().publishEvent(Event.ROLE_DEACTED, roleName, playerAID);
            
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
