package thespian4jade.core.organization;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.behaviours.states.special.ExitValueState;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.states.sender.SendAgreeOrRefuse;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.protocols.organization.subscribetoevent.SubscribeRequestMessage;

/**
 * The 'Subscribe to event' protocol responder party.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class Organization_SubscribeToEvent_ResponderParty
    extends ResponderParty<Organization> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The subscribing player; more precisely its AID.
     * The initiator party of the protocol.
     */
    private AID player;
    
    /**
     * The event to subscribe to.
     */
    private Event event;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Organization_SubscribeToEvent_ResponderParty class.
     * @param message the ACL message initiating the protocol.
     */
    public Organization_SubscribeToEvent_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry.getProtocol(Protocols.SUBSCRIBE_TO_EVENT_PROTOCOL), message);
       
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
        IState receiveSubscribeRequest = new ReceiveSubscribeRequest();
        IState sendSubscribeReply = new SendSubscribeReply();
        IState eventSubscribedTo = new EventSubscribedTo();
        IState eventNotSubscribedTo = new EventNotSubscribedTo();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        registerState(receiveSubscribeRequest);
        registerState(sendSubscribeReply);
        registerLastState(eventSubscribedTo);
        registerLastState(eventNotSubscribedTo);
        
        // Register the transitions.
        initialize.registerDefaultTransition(receiveSubscribeRequest);
        receiveSubscribeRequest.registerTransition(ReceiveSubscribeRequest.PLAYER_IS_EMPLOYED, sendSubscribeReply);
        receiveSubscribeRequest.registerTransition(ReceiveSubscribeRequest.PLAYER_IS_NOT_EMPLOYED, eventNotSubscribedTo);
        sendSubscribeReply.registerDefaultTransition(eventSubscribedTo);
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
                "'Subscribe to event' protocol (id = %1$s) responder party started.",
                getProtocolId()));
        
            player = getACLMessage().getSender();
        }
        
        // </editor-fold>


    }
    
    /**
     * The 'Receive subscribe request' (single receiver) state.
     * A state in which the 'Subscribe request' message is received.
     */
    private class ReceiveSubscribeRequest
        extends ExitValueState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        static final int PLAYER_IS_EMPLOYED = 1;
        static final int PLAYER_IS_NOT_EMPLOYED = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public int doAction() {   
            if (getMyAgent().knowledgeBase.query().doesPlayerEnact(player)) {         
                // The initiator player is employed (enacts a role) in this organization.
                SubscribeRequestMessage message = new SubscribeRequestMessage();
                message.parseACLMessage(getACLMessage());
                event = message.getEvent();
                return PLAYER_IS_EMPLOYED;
            } else {
                // The initiator player is not enacting this role.
                // TODO (priority: low) Send a message to the player exaplaining
                // that a non-enacted role cannot be activated.
                return PLAYER_IS_NOT_EMPLOYED;
            }
        }
        
        // </editor-fold>
    }

    /**
     * The 'Send subscribe reply' (send-agree-or-refuse) state.
     * A state in which the reply message (AGREE or REFUSE) is sent.
     */
    private class SendSubscribeReply extends SendAgreeOrRefuse {

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
            getMyAgent().logInfo("Sending subscribe reply.");
        }

        @Override
        protected int onManager() {
            return event != Event.NONE ? AGREE : REFUSE;
        }

        @Override
        protected String onAgree() {
            getMyAgent().knowledgeBase.update().playerSubscribesToEvent(player, event);
            return "";
        } 

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Subscribe reply sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Event subscribed to' final (one-shot) state.
     */
    private class EventSubscribedTo extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Subscribe to event' protocol (id = %1$s) responder party ended; event subscribed to.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Not subscribed to event' final (one-shot) state.
     */
    private class EventNotSubscribedTo extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Subscribe to event' protocol (id = %1$s) responder party ended; event not subscribed to.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
