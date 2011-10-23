package jadeorg.lang;

import jade.lang.acl.MessageTemplate;

/**
 * A message in an interaction protocol.
 * @author Lukáš Kúdela (2011-10-21)
 * @version 0.1
 */
public abstract class Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Protocol protocol;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public Protocol getProtocol() {
        return protocol;
    }
    
    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
    
    public abstract MessageTemplate getTemplate();
    
    public abstract MessageParser getParser();
    
    public abstract MessageGenerator getGenerator();
    
    // </editor-fold>
}
