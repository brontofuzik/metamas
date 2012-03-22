package thespian4jade.core.player;

import jade.lang.acl.ACLMessage;
import thespian4jade.core.Event;
import thespian4jade.proto.ExitValueState;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Protocols;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.IState;
import thespian4jade.proto.organizationprotocol.publisheventprotocol.EventMessage;
import thespian4jade.util.ClassHelper;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class Player_PublishEvent_ResponderParty extends ResponderParty<Player> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Event event;
    
    private String argument;
    
    private IState selectEventHandler;
    
    private IState end;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Player_PublishEvent_ResponderParty class.
     * @param message 
     */
    public Player_PublishEvent_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.PUBLISH_EVENT_PROTOCOL), message);
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- State -----
        IState receiveEvent = new ReceiveEvent();
        selectEventHandler = new SelectEventHandler();
        end = new End();
        // -----------------
        
        // Registers the states.
        registerFirstState(receiveEvent);
        registerState(selectEventHandler);
        registerLastState(end);
        
        // Register the transitions.
        receiveEvent.registerDefaultTransition(selectEventHandler);
        selectEventHandler.registerTransition(SelectEventHandler.IGNORE_EVENT, end);
    }
    
    /**
     * Selects the event handler handling the given event.
     * @param event the event to handle
     * @return the selected event handler
     */
    private EventHandler selectEventHandler(Event event) {
        Class eventHandlerClass = getMyAgent().eventHandlers.get(event);
        if (eventHandlerClass != null) {
            // The event is handled.
            EventHandler eventHandler = ClassHelper.instantiateClass(eventHandlerClass);
        
            // Register the event handler state.
            registerState(eventHandler);

            // Register the transitions.
            selectEventHandler.registerTransition(SelectEventHandler.HANDLE_EVENT, eventHandler);
            eventHandler.registerDefaultTransition(end);

            return eventHandler;
        } else {
            // The event is ignored.
            return null;
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Receive event' (one-shot) state.
     */
    private class ReceiveEvent extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Publish event' protocol (id = %1$s) responder party started.",
                getProtocolId()));
            
            EventMessage message = new EventMessage();
            message.parseACLMessage(getACLMessage());
            
            event = message.getEvent();
            argument = message.getArgument();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Select event handler' (one-shot) state.
     */
    private class SelectEventHandler extends ExitValueState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int HANDLE_EVENT = 1;
        public static final int IGNORE_EVENT = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int doAction() {
            EventHandler eventHandler = selectEventHandler(event);
            if (eventHandler != null) {
                // The event is handled.
                eventHandler.setArgument(argument);
                return HANDLE_EVENT;
            } else {
                // The event is ignored.
                return IGNORE_EVENT;
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'End' (one-shot) state.
     */
    private class End extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // LOG
            getMyAgent().logInfo(String.format(
                "'Publish event' protocol (id = %1$s) responder party ended.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
