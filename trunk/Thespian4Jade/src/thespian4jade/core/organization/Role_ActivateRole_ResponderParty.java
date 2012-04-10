package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.protocols.role.activaterole.ActivateRequestMessage;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.sender.SendAgreeOrRefuse;
import thespian4jade.behaviours.states.OneShotBehaviourState;

/**
 * An 'Activate role' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2011-12-10
 * @version %I% %G%
 */
public class Role_ActivateRole_ResponderParty extends ResponderParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player requesting the role activation; more precisely its AID.
     * The initiator party.
     */
    private AID player;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the Role_ActivateRole_ResponderParty class.
     * @param aclMessage the ACL message initiating the protocol
     */
    public Role_ActivateRole_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry.getProtocol(Protocols.ACTIVATE_ROLE_PROTOCOL), aclMessage);
        
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
        IState receiveActivateRequest = new ReceiveActivateRequest();
        IState sendActivateReply = new SendActivateReply();
        IState roleActivated = new RoleActivated();
        IState roleNotActivated = new RoleNotActivated();
        // ------------------

        // Register states.
        registerFirstState(initialize);       
        registerState(receiveActivateRequest);
        registerState(sendActivateReply);        
        registerLastState(roleActivated);
        registerLastState(roleNotActivated);

        // Register transitions.
        initialize.registerDefaultTransition(receiveActivateRequest);
        receiveActivateRequest.registerTransition(ReceiveActivateRequest.ROLE_ENACTED, sendActivateReply);
        receiveActivateRequest.registerTransition(ReceiveActivateRequest.ROLE_NOT_ENACTED, roleNotActivated);       
        sendActivateReply.registerTransition(SendActivateReply.AGREE, roleActivated);
        sendActivateReply.registerTransition(SendActivateReply.REFUSE, roleNotActivated);
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
                "'Activate role' protocol (id = %1$s) responder party started.",
                getProtocolId()));
            
            player = getACLMessage().getSender();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive activate request' (exit value) state.
     * A state in which the 'Activate request' message is received.
     */
    private class ReceiveActivateRequest extends ExitValueState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        static final int ROLE_ENACTED = 1;
        static final int ROLE_NOT_ENACTED = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
       
        @Override
        protected int doAction() {            
            if (player.equals(getMyAgent().enactingPlayer)) {
                // The initiator player is the player enacting this role.
                ActivateRequestMessage message = new ActivateRequestMessage();
                message.parseACLMessage(getACLMessage());
                return ROLE_ENACTED;
            } else {
                // The initiator player is not the player enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a non-enacted role cannot be activated.
                return ROLE_NOT_ENACTED;
            }
        }

        // </editor-fold>
    }

    /**
     * The 'Send activate reply' (send-agree-or-refuse) state.
     * A state in which the reply message (AGREE or REFUSE) is sent.
     */
    private class SendActivateReply extends SendAgreeOrRefuse {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { player };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Sending activate reply.");
        }

        @Override
        protected int onManager() {
            return (getMyAgent().isActivable()) ? AGREE : REFUSE;
        }
        
        @Override
        protected String onAgree() {
            getMyAgent().activate();
            return "";
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Activate reply sent.");
        }
        
        // </editor-fold>
    }

    /**
     * The 'Role activated' final (one-shot) state.
     */
    private class RoleActivated extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // Publish the 'Role activated' event.
            getMyAgent().myOrganization.publishEvent(Event.ROLE_ACTIVATED,
                getMyAgent().getRoleName(), player);
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) responder party ended; role was activated.",
                getProtocolId()));
        }

        // </editor-fold>
    }

    /**
     * The 'Role not activated' final (one-shot) state.
     */
    private class RoleNotActivated extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) responder party ended; role was not activated.",
                getProtocolId()));
        }

        // </editor-fold>
    }

    // </editor-fold>
}
