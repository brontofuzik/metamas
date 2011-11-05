package jadeorg.proto;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import java.util.Hashtable;
import java.util.Map;

/**
 * An interaction protocol.
 * DP: Abstract factory - Abstract factory
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public abstract class Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<Class, Message> messages = new Hashtable<Class, Message>();
    
    private Map<Class, Party> parties = new Hashtable<Class, Party>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the protocol name.
     * NOTE FOR DEVELOPERS:
     * Implement this getter by simply returning the protocol name.
     * @return the protocol name.
     */
    public abstract String getName();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Registers a message with this protocol.
     * @param message the message.
     */
    public void registerMessage(Message message) {
        // ----- Preconditions -----
        if (message == null) {
            throw new NullPointerException();
        }
        // -------------------------
        
        messages.put(message.getClass(), message);
    }
    
    public void registerParty(Party party) {
        // ----- Preconditions -----
        if (party == null) {
            throw new NullPointerException();
        }
        // -------------------------
        
        parties.put(party.getClass(), party);
    }
    
    // Convenience method.
    // TODO Replace the messageClass parameter with generics.
    public MessageTemplate getTemplate(Class messageClass) {
        return getMessage(messageClass).getTemplate();
    }
    
    public MessageTemplate getTemplate() {
        return MessageTemplate.MatchProtocol(getName());
    }
    
    // Convenience method.
    // TODO Replace the messageClass parameter with generics.
    public Message parse(Class messageClass, ACLMessage aclMessage) {
        return getParser(messageClass).parse(aclMessage);
    }
    
    // Convenience method.
    // TODO Replace the messageClass parameter with generics.
    public ACLMessage generate(Class messageClass, Message message) {
        return getGenerator(messageClass).generate(message);
    }
    
    public ACLMessage getACLMessage(int performative) {
        ACLMessage aclMessage = new ACLMessage(performative);
        aclMessage.setProtocol(getName());
        return aclMessage;
    }
    
    // ---------- PRIVATE ----------
    
    /**
     * Gets a message parser for a given message class.
     * @param messageClass the message class.
     * @return the message parser for the given message class.
     */
    private MessageParser getParser(Class messageClass) {
        return getMessage(messageClass).getParser();
    }
    
    /**
     * Gets a message generator for a given message class.
     * @param messageClass the message class.
     * @return the message generator for the given message class.
     */
    private MessageGenerator getGenerator(Class messageClass) {
        return getMessage(messageClass).getGenerator();
    }
    
    private Message getMessage(Class messageClass) {
        return messages.get(messageClass);
    }
    
    // </editor-fold>
}
