package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.behaviours.ExitValueState;
import thespian4jade.protocols.ProtocolRegistry_StaticClass;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.protocols.organizationprotocol.deactroleprotocol.DeactRequestMessage;
import thespian4jade.behaviours.jadeextensions.IState;
import thespian4jade.behaviours.senderstates.SendAgreeOrRefuse;
import thespian4jade.behaviours.jadeextensions.OneShotBehaviourState;

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
    
    private String roleName;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Organization_DeactRole_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.DEACT_ROLE_PROTOCOL), aclMessage);

        player = getACLMessage().getSender();

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
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();

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
    
    private class Initialize extends ExitValueState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int OK = 1;
        public static final int FAIL = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int doAction() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deact role' protocol (id = %1$s) responder party started.",
                getProtocolId()));
            
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
        protected void onAgree() {
            // Update the knowledge base.
            getMyAgent().knowledgeBase
                .update().roleIsDeacted(roleName, player);
            
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
            getMyAgent().publishEvent(Event.ROLE_DEACTED, roleName, player);
            
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
