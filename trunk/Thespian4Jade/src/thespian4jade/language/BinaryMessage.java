package thespian4jade.language;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.io.Serializable;

/**
 * A message whose payload is interpreted as a serialized object.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class BinaryMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the BinaryMessage class.
     * @param performative the performative
     */
    protected BinaryMessage(int performative) {
        super(performative);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Generates the ACL message.
     * @return the generated ACL message
     */
    @Override
    public ACLMessage generateACLMessage() {
        ACLMessage aclMessage = new ACLMessage(getPerformative());
        try {
            aclMessage.setContentObject(getContentObject());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return aclMessage;
    }

    /**
     * Parses the ACL message
     * @param aclMessage the ACL message to parse
     */
    @Override
    public void parseACLMessage(ACLMessage aclMessage) {
        try {
            setContentObject(aclMessage.getContentObject());
        } catch (UnreadableException ex) {
            ex.printStackTrace();
        }
    }
    
    // ----- PROTECTED -----
    
    /**
     * Gets the content object for the ACL message.
     * @return the content object for the ACL message
     */
    protected abstract Serializable getContentObject();
    
    /**
     * Sets the content object from the ACL message.
     * @param contentObject the content object from the ACL message
     */
    protected abstract void setContentObject(Serializable contentObject);
    
    // </editor-fold>
}
