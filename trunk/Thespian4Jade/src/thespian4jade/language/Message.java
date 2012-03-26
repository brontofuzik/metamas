package thespian4jade.language;

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
    
    /**
     * The performative.
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
     * @param performative 
     */
    protected Message(int performative) {
        this.performative = performative;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">

    /**
     * Gets the performative.
     * @return the performative
     */
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
    
    /**
     * Generates the ACL message.
     * @return the generated ACL message
     */
    public abstract ACLMessage generateACLMessage();
    
    /**
     * Parses the ACL message
     * @param aclMessage the ACL message to parse
     */
    public abstract void parseACLMessage(ACLMessage aclMessage);
    
    // </editor-fold>
}
