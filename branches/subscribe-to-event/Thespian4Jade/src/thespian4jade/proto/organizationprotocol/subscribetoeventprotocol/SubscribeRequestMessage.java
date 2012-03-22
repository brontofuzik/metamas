package thespian4jade.proto.organizationprotocol.subscribetoeventprotocol;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.core.Event;
import thespian4jade.lang.TextMessage;

/**
 * A 'Request subscribe' (text) message.
 * This message is sent by the 'Subscribe to event' protocol initiator (a player)
 * to the protocol responder (an organization) and coitains a request
 * to subscribe to an event.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class SubscribeRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The event to subscribe to.
     */
    private Event event;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the SubscribeRequestMessage class.
     */
    public SubscribeRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the event to subscribe to.
     * @return the event to subscribe to
     */
    public Event getEvent() {
        return event;
    }
    
    /**
     * Sets the event to subscribe to.
     * @param event the event to subscribe to
     * @return this 'Subscribe request' message
     */
    public SubscribeRequestMessage setEvent(Event event) {
        this.event = event;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected String generateContent() {
        return String.format("subscribe-to-event(%1$s)", event);
    }

    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("subscribe-to-event\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        event = Event.fromString(matcher.group(1));
    }
    
    // </editor-fold>
}
