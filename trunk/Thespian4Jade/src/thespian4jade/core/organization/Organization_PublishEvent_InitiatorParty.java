package thespian4jade.core.organization;

import jade.core.AID;
import java.util.Set;
import thespian4jade.core.Event;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.protocols.organization.publishevent.EventMessage;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class Organization_PublishEvent_InitiatorParty extends InitiatorParty<Organization> {
 
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The players listening for the event; more precisely their AIDs.
     * The reposnder parties.
     */
    private AID[] players;
    
    /**
     * The event to publish.
     */
    private Event event;
    
    /**
     * The event argument.
     */
    private String argument;
    
    /**
     * The player to exclude.
     */
    private AID playerToExclude;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Organization_PublishEvent_InitiatorParty class.
     * @param protocol the 'Publish event' protocol.
     * @param event the event to publish
     * @param argument the event argument
     * @param playerToExclude the player to exclude
     */
    public Organization_PublishEvent_InitiatorParty(final Event event,
        final String argument, final AID playerToExclude) {
        super(ProtocolRegistry.getProtocol(Protocols.PUBLISH_EVENT_PROTOCOL));
        // ----- Preconditions -----
        assert event != Event.NONE;
        // -------------------------
        
        this.event = event;
        this.argument = argument;
        this.playerToExclude = playerToExclude;
        
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
        IState sendEvent = new SendEvent();
        IState eventPublished = new EventPublished();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        registerState(sendEvent);
        registerLastState(eventPublished);
        
        // Register the transitions.
        initialize.registerDefaultTransition(sendEvent);
        sendEvent.registerDefaultTransition(eventPublished);
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
                "'Publish event' protocol (id = %1$s) initiator party started.",
                getProtocolId()));
            
            // Get all players subscribed to the event.
            Set<AID> subscribedPlayers = getMyAgent().knowledgeBase
                .query().getPlayersSubscribedToEvent(event);           
            subscribedPlayers.remove(playerToExclude);
            
            players = subscribedPlayers.toArray(new AID[0]);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send event' (single sender) state.
     * A state in which the 'Event' message is sent.
     */
    private class SendEvent extends SingleSenderState<EventMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return players;
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Sending event.");
        }
        
        @Override
        protected EventMessage prepareMessage() {
            EventMessage message = new EventMessage();
            message.setEvent(event);
            message.setArgument(argument);
            return message;
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Event sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Event published' final (one-shot) state.
     */
    private class EventPublished extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Publish event' protocol (id = %1$s) initiator party ended; event published.",
                getProtocolId()));
        }
        
        // </editor-fold>  
    }
    
    // </editor-fold>
}
