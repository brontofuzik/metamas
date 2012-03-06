package thespian4jade.lang;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.io.Serializable;

/**
 * An binary message.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class BinaryMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected BinaryMessage(int performative) {
        super(performative);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
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

    @Override
    public void parseACLMessage(ACLMessage aclMessage) {
        try {
            setContentObject(aclMessage.getContentObject());
        } catch (UnreadableException ex) {
            ex.printStackTrace();
        }
    }
    
    // ----- PROTECTED -----
    
    protected abstract Serializable getContentObject();
    
    protected abstract void setContentObject(Serializable contentObject);
    
    // </editor-fold>
}
