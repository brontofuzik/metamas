package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.protocols.ExitValueState;
import thespian4jade.protocols.ProtocolRegistry_StaticClass;
import thespian4jade.protocols.Protocols;
import thespian4jade.protocols.ResponderParty;
import thespian4jade.protocols.roleprotocol.activateroleprotocol.ActivateRequestMessage;
import thespian4jade.protocols.jadeextensions.IState;
import thespian4jade.protocols.SendAgreeOrRefuse;
import thespian4jade.protocols.jadeextensions.OneShotBehaviourState;

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

    public Role_ActivateRole_ResponderParty(ACLMessage aclMessage) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.ACTIVATE_ROLE_PROTOCOL), aclMessage);
        
        player = getACLMessage().getSender();
        
        buildFSM();
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    private void buildFSM() {
        // ----- States -----
        IState initialize = new Initialize();
        IState receiveActivateRequest = new ReceiveActivateRequest();
        IState sendActivateReply = new SendActivateReply();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------

        // Register states.
        registerFirstState(initialize);       
        registerState(receiveActivateRequest);
        registerState(sendActivateReply);        
        registerLastState(successEnd);
        registerLastState(failureEnd);

        // Register transitions.
        initialize.registerTransition(Initialize.OK, receiveActivateRequest);
        initialize.registerTransition(Initialize.FAIL, failureEnd);        
        receiveActivateRequest.registerDefaultTransition(sendActivateReply);       
        sendActivateReply.registerTransition(SendActivateReply.AGREE, successEnd);
        sendActivateReply.registerTransition(SendActivateReply.REFUSE, failureEnd);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * The 'Initialize' (initialize) state.
     */
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
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) responder party started.",
                getProtocolId()));
        
            if (player.equals(getMyAgent().enactingPlayer)) {
                // The initiator player is enacting this role.
                return OK;
            } else {
                // The initiator player is not enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a non-enacted role cannot be activated.
                return FAIL;
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
            message.parseACLMessage(getACLMessage());
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
        protected void onAgree() {
            getMyAgent().activate();
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Activate reply sent.");
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
            // Publish the 'Role activated' event.
            getMyAgent().myOrganization.publishEvent(Event.ROLE_ACTIVATED,
                getMyAgent().getRoleName(), player);
            
            // LOG
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) responder party succeeded.",
                getProtocolId()));
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
            // LOG
            getMyAgent().logInfo(String.format(
                "'Activate role' protocol (id = %1$s) responder party failed.",
                getProtocolId()));
        }

        // </editor-fold>
    }

    // </editor-fold>
}
