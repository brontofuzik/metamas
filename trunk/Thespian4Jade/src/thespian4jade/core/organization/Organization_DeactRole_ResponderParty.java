package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.protocols.organization.deactrole.DeactRequestMessage;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.sender.SendAgreeOrRefuse;
import thespian4jade.behaviours.states.OneShotBehaviourState;

/**
 * A 'Deact role' protocol responder party (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-21
 * @version %I% %G%
 */
public class Organization_DeactRole_ResponderParty extends ResponderParty<Organization> {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player requesting the role deactment; more precisely its AID.
     * The initiator party.
     */
    private AID player;
    
    /**
     * The name of the role to deact.
     */
    private String roleName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the Organization_DeactRole_ResponderParty class.
     * @param aclMessage the ACL message initiating the protocol
     */
    public Organization_DeactRole_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry.getProtocol(Protocols.DEACT_ROLE_PROTOCOL), aclMessage);

        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        IState initialize = new Initialize();
        IState receiveDeactRequest = new ReceiveDeactRequest();
        IState sendDeactReply = new SendDeactReply();
        IState roleDeacted = new SuccessEnd();
        IState roleEnacted = new FailureEnd();

        // Register the states.
        registerFirstState(initialize);      
        registerState(receiveDeactRequest);
        registerState(sendDeactReply);      
        registerLastState(roleDeacted);
        registerLastState(roleEnacted);
        
        // Register the transisions.
        initialize.registerDefaultTransition(receiveDeactRequest);    
        receiveDeactRequest.registerDefaultTransition(sendDeactReply);
        sendDeactReply.registerTransition(SendAgreeOrRefuse.AGREE, roleDeacted);
        sendDeactReply.registerTransition(SendAgreeOrRefuse.REFUSE, roleEnacted);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' initial (one-shot) state.
     * A state in which the party is initialized.
     */
    private class Initialize extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deact role' protocol (id = %1$s) responder party started.",
                getProtocolId()));
            
            player = getACLMessage().getSender();
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
     * The 'Send deact reply' (send-agree-or-refuse) state.
     * A state in which the 'Enact reply' message is sent.
     */
    private class SendDeactReply extends SendAgreeOrRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
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
                if (getMyAgent().knowledgeBase.query().isRoleEnactedByPlayer(roleName, player)) {
                    // The is enacted by the player.
                    return AGREE;
                } else {
                    // The role is not enacted by the player.
                    return REFUSE;
                }
            } else {
                // The role is not defined for this organization.
                return REFUSE;
            }
        }
        
        @Override
        protected String onAgree() {
            // Update the knowledge base.
            getMyAgent().knowledgeBase
                .update().roleIsDeacted(roleName, player);
            
            // Stop the role agent.
            // TODO (priority: medium) Implement.
            
            // Unlink the position from its organization.
            // TODO (priority: medium) Implement.
            
            // Destroy the role agent.
            // TODO (priority: medium) Implement.
            return "";
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deact reply sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Role deacted' final (one-shot) state.
     */
    private class SuccessEnd extends OneShotBehaviourState {      
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Publish the 'Role deacted' event.
            getMyAgent().publishEvent(Event.ROLE_DEACTED, roleName, player);
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deact role' protocol (id = %1$s) responder party ended; role was enacted.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Role Not Deacted' final (one-shot) state.
     */
    private class FailureEnd extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deact role' protocol (id = %1$s) responder party ended; role was not enacted.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
