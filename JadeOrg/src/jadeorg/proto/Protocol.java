package jadeorg.proto;

import jade.core.Agent;
import jade.lang.acl.MessageTemplate;

/**
 * An interaction protocol.
 * DP: Abstract factory - Abstract factory
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
}
