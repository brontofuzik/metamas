package thespian4jade.proto.organizationprotocol.publisheventprotocol;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.lang.TextMessage;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public class EventMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The event (enact-role, deact-role, activate-role or deactivate-role).
     */
    private String event;
    
    /**
     * The event argument.
     */
    private String argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EventMessage class.
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
    public String getEvent() {
        return event;
    }
    
    /**
     * Sets the event.
     * @param event the event
     * @return this 'Event' message
     */
    public EventMessage setEvent(String event) {
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
     * @return this 'Event' message
     */
    public EventMessage setArgument(String argument) {
        this.argument = argument;
        return this;
    } 
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected String generateContent() {
        return String.format("event(%1$s,%2$s)", event, argument);
    }

    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("event\\((.*),(.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        event = matcher.group(1);
        argument = matcher.group(2);
    }
    
    // </editor-fold>
}
