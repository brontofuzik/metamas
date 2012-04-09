package thespian4jade.protocols.organization.subscribetoevent;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.core.Event;
import thespian4jade.language.TextMessage;

/**
 * A 'Request subscribe' (text) message is sent by the 'Subscribe to event'
 * protocol initiator (a player) to the protocol responder (an organization)
 * and contains a request to subscribe to an event.
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
     * The corresponding ACL message has the REQUEST performative.
     */
    public SubscribeRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the event to subscribe to.
     * @return the event
     */
    public Event getEvent() {
        return event;
    }
    
    /**
     * Sets the event to subscribe to.
     * @param event the event
     * @return this 'Subscribe request' message (Design pattern: Fluent interface)
     */
    public SubscribeRequestMessage setEvent(Event event) {
        this.event = event;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the ACL message
     */
    @Override
    protected String generateContent() {
        return String.format("subscribe-to-event(%1$s)", event);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("subscribe-to-event\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        event = Event.fromString(matcher.group(1));
    }
    
    // </editor-fold>
}
