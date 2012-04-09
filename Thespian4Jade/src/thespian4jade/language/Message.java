package thespian4jade.language;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 * A message exchanged in a protocol.
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public abstract class Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The performative of the corresponding ACL message.
     */
    private int performative;
    
    /**
     * The sender; more precisely its AID.
     */
    private AID sender;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Message class.
     * @param performative the performative of the corresponding message
     */
    protected Message(int performative) {
        this.performative = performative;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    /**
     * Gets the performative of the corresponding ACL message.
     * @return the performative
     */
    public int getPerformative() {
        return performative;
    }
    
    /**
     * Gets the sender; more precisely its AID.
     * @return the sender
     */
    public AID getSender() {
        return sender;
    }
    
    /**
     * Sets the sender; more precisely its AID.
     * @param sender the sender
     */
    public void setSender(AID sender) {
        this.sender = sender;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Generates the corresponding ACL message.
     * @return the corresponding ACL message
     */
    public abstract ACLMessage generateACLMessage();
    
    /**
     * Parses the corresponding ACL message.
     * @param aclMessage the corresponding ACL message
     */
    public abstract void parseACLMessage(ACLMessage aclMessage);
    
    // </editor-fold>
}
