package thespian4jade.protocols.organization.publishevent;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.core.Event;
import thespian4jade.language.TextMessage;

/**
 * A 'Event' (text) message is sent by the 'Publish event'
 * protocol initiator (an organization) to the protocol responder (an player)
 * and contains an event and its argument.
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class EventMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The event (enact-role, deact-role, activate-role or deactivate-role).
     */
    private Event event;
    
    /**
     * The event argument.
     */
    private String argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EventMessage class.
     * The corresponding ACL message has the INFORM performative.
     */
    public EventMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the event.
     * @return the event
     */
    public Event getEvent() {
        return event;
    }
    
    /**
     * Sets the event.
     * @param event the event
     * @return this 'Event' message (Design pattern: Fluent interface)
     */
    public EventMessage setEvent(Event event) {
        this.event = event;
        return this;
    }
    
    /**
     * Gets the event argument.
     * @return the event argument
     */
    public String getArgument() {
        return argument;
    }
    
    /**
     * Sets the event argument.
     * @param argument the event argument
     * @return this 'Event' message (Design pattern: Fluent interface)
     */
    public EventMessage setArgument(String argument) {
        this.argument = argument;
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
        return String.format("event(%1$s,%2$s)", event.toString(), argument);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("event\\((.*),(.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        event = Event.fromString(matcher.group(1));
        argument = matcher.group(2);
    }
    
    // </editor-fold>
}
