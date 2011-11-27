package jadeorg.lang;

import jade.core.AID;
import jadeorg.proto.Protocol;
import jade.lang.acl.MessageTemplate;
import java.util.ArrayList;
import java.util.List;

/**
 * A message in an interaction protocol.
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public abstract class Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The associated protocol. */
    private Protocol protocol;
    
    private AID sender;
    
    private List<AID> receivers = new ArrayList<AID>();
    
    /** The message template singleton. */
    private MessageTemplate templateSingleton;
    
    /** The message parser singleton. */
    private MessageParser parserSingleton;
    
    /** The message generator singleton. */
    private MessageGenerator generatorSingleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the associated protocol.
     * @return the associated protocol
     */
    public Protocol getProtocol() {
        return protocol;
    }
    
    /**
     * Sets the associated protocol.
     * @param protocol the associated protocol
     */
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
    
    public AID getSender() {
        return sender;
    }
    
    public Message setSender(AID sender) {
        this.sender = sender;
        return this;
    }
    
    public AID[] getReceivers() {
        return receivers.toArray(new AID[receivers.size()]);
    }
    
    public Message addReceiver(AID receiver) {        
        // ----- Preconditions -----
        assert !receivers.contains(receiver);
        // -------------------------
        
        receivers.add(receiver);
        return this;
    }
    
    /**
     * Gets the template to receive this message.
     * @return the template to receive this message 
     */
    public MessageTemplate getTemplate() {
        if (templateSingleton == null) {
            templateSingleton = createTemplate();
        }
        return templateSingleton;
    }
    
    /**
     * Gets the message parser.
     * @return the message parser
     */
    public MessageParser getParser() {
        if (parserSingleton == null) {
            parserSingleton = createParser();
        }
        return parserSingleton;
    }
    
    /**
     * Gets the message parser.
     * @return the message generator
     */
    public MessageGenerator getGenerator() {
        if (generatorSingleton == null) {
            generatorSingleton = createGenerator();
        }
        return generatorSingleton;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected abstract MessageTemplate createPerformativeTemplate();
    
    protected abstract MessageParser createParser();
    
    protected abstract MessageGenerator createGenerator();
      
    // ---------- PRIVATE ----------
    
    private MessageTemplate createTemplate() {
        MessageTemplate template = createProtocolTemplate();
        MessageTemplate performativeTemplate = createPerformativeTemplate();
        if (performativeTemplate != null) {
            template = MessageTemplate.and(
                template,
                performativeTemplate);
        }
        return template;
    }
    
    private MessageTemplate createProtocolTemplate() {
        return MessageTemplate.MatchProtocol(protocol.getName());
    }
    
    // </editor-fold>
}
