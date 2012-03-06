package thespian4jade.lang;

import jade.core.AID;
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
    
    private AID sender;
    
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
    
    /**
     * Gets the sender; more precisely its AID.
     * @return the sender; more precisely its AID
     */
    public AID getSender() {
        return sender;
    }
    
    /**
     * Sets the sender; more precisely its AID.
     * @param sender the sender; more precsely its AID
     */
    public void setSender(AID sender) {
        this.sender = sender;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public abstract ACLMessage generateACLMessage();
    
    public abstract void parseACLMessage(ACLMessage aclMessage);
    
    // </editor-fold>
}
