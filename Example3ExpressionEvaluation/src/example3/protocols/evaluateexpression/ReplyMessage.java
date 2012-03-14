package example3.protocols.evaluateexpression;

import jade.lang.acl.ACLMessage;
import java.io.Serializable;
import thespian4jade.lang.BinaryMessage;
import thespian4jade.lang.MessageFactory;

/**
 * A 'Reply' (binary) message.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class ReplyMessage extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ReplyMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public int getValue() {
        return value;
    }
    
    public ReplyMessage setValue(int value) {
        this.value = value;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected Serializable getContentObject() {
        return new Integer(value);
    }

    @Override
    protected void setContentObject(Serializable contentObject) {
        value = ((Integer)contentObject).intValue();
    }
 
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    public static class Factory implements MessageFactory<ReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public ReplyMessage createMessage() {
            return new ReplyMessage();
        }        
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
