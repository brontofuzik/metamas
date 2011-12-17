package jadeorg.proto;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.simplemessages.SimpleMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.lang.simplemessages.AgreeMessage;
import jadeorg.lang.simplemessages.FailureMessage;
import jadeorg.lang.simplemessages.RefuseMessage;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
    
    private String name;
    
    private Map<Class, Message> messages = new Hashtable<Class, Message>();
    
    // TAG NOT-USED
    private Map<String, Class> parties = new Hashtable<String, Class>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Protocol(String name) {
        this.name = name;
        registerMessages();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the protocol name.
     * @return the protocol name
     */
    public String getName() {
        return name;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
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
        ACLMessage aclMessage = getGenerator(messageClass).generate(message);
        aclMessage.setProtocol(getName());
        return aclMessage;
    }
    
    // ---------- PROTECTED ----------
    
    /**
     * Registers a message with this protocol.
     * @param message the message.
     */
    protected void registerMessage(Class messageClass) {
        // ----- Preconditions -----
        if (messageClass == null) {
            throw new IllegalArgumentException("messageClass");
        }
        // -------------------------
        
        Message message = instantiateMessageClass(messageClass);
        messages.put(messageClass, message);
        message.setProtocol(this);
    }
    
    // TAG NOT-USED
    protected void registerParty(Class partyClass) {
        // ----- Preconditions -----
        if (partyClass == null) {
            throw new IllegalArgumentException("partyClass");
        }
        // -------------------------
        
        parties.put(partyClass.getName(), partyClass);
    }
    
    // ---------- PRIVATE ----------
    
    private void registerMessages() {
        registerMessage(AgreeMessage.class);
        registerMessage(RefuseMessage.class);
        registerMessage(FailureMessage.class);
    }
    
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
    
    private Message instantiateMessageClass(Class messageClass) {
        Constructor constructor = null;
        try {
            constructor = messageClass.getConstructor(new Class[0]);
        } catch (NoSuchMethodException ex) {
            ex.printStackTrace();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        
        Message message = null;
        try {
            message = (Message)constructor.newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }
        
        return message;
    }
    
    private Message getMessage(Class messageClass) {
        return messages.get(messageClass);
    }
    // </editor-fold>
}
