package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.protocols.role.deactivaterole.DeactivateRequestMessage;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.sender.SendAgreeOrRefuse;
import thespian4jade.behaviours.states.OneShotBehaviourState;

/**
 * A 'Deactivate role' protocol responder (new version).
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public class Role_DeactivateRole_ResponderParty extends ResponderParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player requesting the role deactivation; more precisely its AID.
     * The initiator party.
     */
    private AID player;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the Role_DeactivateRole_ResponderParty class.
     * @param message the ACL message initiating the protocol 
     */
    public Role_DeactivateRole_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry.getProtocol(Protocols.DEACTIVATE_ROLE_PROTOCOL), message);

        buildFSM();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new Initialize();
        IState receiveActivateRequest = new ReceiveDeactivateRequest();
        IState sendActivateReply = new SendDeactivateReply();
        IState roleDeactivated = new RoleDeactivated();
        IState roleNotDeactivated = new RoleNotDeactivated();
        // ------------------

        // Register states.
        registerFirstState(initialize);        
        registerState(receiveActivateRequest);
        registerState(sendActivateReply);       
        registerLastState(roleDeactivated);
        registerLastState(roleNotDeactivated);

        // Register transitions.
        initialize.registerDefaultTransition(receiveActivateRequest);
        receiveActivateRequest.registerTransition(ReceiveDeactivateRequest.ROLE_ENACTED, sendActivateReply);  
        receiveActivateRequest.registerTransition(ReceiveDeactivateRequest.ROLE_NOT_ENACTED, roleNotDeactivated);     
        sendActivateReply.registerTransition(SendDeactivateReply.AGREE, roleDeactivated);
        sendActivateReply.registerTransition(SendDeactivateReply.REFUSE, roleNotDeactivated);
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
                "'Deactivate role' protocol (id = %1$s) responder party started.",
                getProtocolId()));
        
            player = getACLMessage().getSender();
            

        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive deactivate request' (single receiver) state.
     * A state in which the 'Deactivate request' message is received.
     */
    private class ReceiveDeactivateRequest extends ExitValueState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        static final int ROLE_ENACTED = 1;
        static final int ROLE_NOT_ENACTED = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int doAction() {            
            if (player.equals(getMyAgent().enactingPlayer)) {
                // The initiator player is enacting this role.
                DeactivateRequestMessage message = new DeactivateRequestMessage();
                message.parseACLMessage(getACLMessage());
                return ROLE_ENACTED;
            } else {
                // The initiator player is not enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a non-enacted role cannot be deactivated.
                return ROLE_NOT_ENACTED;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send deactivate reply' (send-agree-or-refuse) state.
     * A state in which the reply message (AGREE or REFUSE) is sent.
     */
    private class SendDeactivateReply extends SendAgreeOrRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending deactivate reply.");
        }
        
        @Override
        protected int onManager() {
            if (getMyAgent().isDeactivable()) {            
                return AGREE;
            } else {
                return REFUSE;
            }
        }
        
        @Override
        protected String onAgree() {
            getMyAgent().deactivate();
            return "";
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Deactivate reply sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Role deactivated' final (one-shot) state.
     */
    private class RoleDeactivated extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // Publish the 'Role deactivated' event.
            getMyAgent().myOrganization.publishEvent(Event.ROLE_DEACTIVATED,
                getMyAgent().getRoleName(), player);
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deactivate role' protocol (id = %1$s) responder party ended; role was deactivated.",
                getProtocolId()));
        }

        // </editor-fold>
    }
        
    /**
     * The 'Role not deactivated' (simple) state.
     */
    private class RoleNotDeactivated extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Deactivate role' protocol (id = %1$s) responder party ended; role was not deactivated.",
                getProtocolId()));
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
