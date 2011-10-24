package jadeorg.lang;

import jadeorg.proto.Protocol;
import jade.lang.acl.MessageTemplate;

/**
 * A message in an interaction protocol.
 * @author Lukáš Kúdela (2011-10-21)
 * @version 0.1
 */
public abstract class Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The associated protocol. */
    private Protocol protocol;
    
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
    
    // ---------- PROTECTED ----------
    
    protected abstract int getPerformative();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected abstract MessageParser createParser();
    
    protected abstract MessageGenerator createGenerator();
    
    
    // ---------- PRIVATE ----------
    
    private MessageTemplate createTemplate() {
        return MessageTemplate.and(
            MessageTemplate.MatchPerformative(getPerformative()),
            MessageTemplate.MatchProtocol(protocol.getName()));
    }
    
    // </editor-fold>
}
