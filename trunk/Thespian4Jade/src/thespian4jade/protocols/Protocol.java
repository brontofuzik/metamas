package thespian4jade.protocols;

import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.behaviours.parties.ResponderParty;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * An interaction protocol.
 * Design pattern: Abstract factory - Abstract factory (GoF)
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public abstract class Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of this protocol.
     * @return the name of this protocol
     */
    public String getName() {
        return getClass().getName();
    }
    
    /**
     * Gets a message template matching this protocol.
     * @return a message template matching this protocol
     */
    public MessageTemplate getTemplate() {
        return MessageTemplate.MatchProtocol(getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    public abstract InitiatorParty createInitiatorParty(Object... arguments);
    
    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    public abstract ResponderParty createResponderParty(ACLMessage message);
    
    // </editor-fold>
}
