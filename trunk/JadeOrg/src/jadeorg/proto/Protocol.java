package jadeorg.proto;

import jade.lang.acl.MessageTemplate;

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
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Protocol(String name) {
        this.name = name;
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
    
    public MessageTemplate getTemplate() {
        return MessageTemplate.MatchProtocol(name);
    }
    
    // </editor-fold>
}
