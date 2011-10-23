package jadeorg.lang;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import java.util.Hashtable;
import java.util.Map;

/**
 * An interaction protocol.
 * DP: Abstract factory - Abstract factory
 * @author Lukáš Kúdela (2011-1-21)
 * @version 0.1
 */
public abstract class Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<Class, Message> messages = new Hashtable<Class, Message>(); 
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected void Protocol() {
        registerMessages();
    }
    
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
     * Registers messages with this protocol.
     * NOTE FOR DEVELOPERS:
     * Implement this method by registering all messages of this protocol.
     */
    public abstract void registerMessages();
    
    /**
     * Registers a message with this protocol.
     * @param message the message.
     */
    public void registerMessage(Message message) {            
        messages.put(message.getClass(), message);
    }
    
    // Convenience method.
    // TODO Replace the messageClass parameter with generics.
    public MessageTemplate getTemplate(Class messageClass) {
        return getMessage(messageClass).getTemplate();
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
