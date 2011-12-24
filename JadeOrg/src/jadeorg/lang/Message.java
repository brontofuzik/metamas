package jadeorg.lang;

import jade.lang.acl.ACLMessage;

/**
 * A message in an interaction protocol.
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public abstract class Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int performative;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Message(int performative) {
        this.performative = performative;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    // TODO Remove.
    public int getPerformative() {
        return performative;
    }
    
    // TODO Remove.
    public void setPerformative(int performative) {
        this.performative = performative;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public abstract ACLMessage generateACLMessage();
    
    public abstract void parseACLMessage(ACLMessage aclMessage);
    
    // </editor-fold>
}
