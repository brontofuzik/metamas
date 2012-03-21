package example2.protocols.evaluateexpression;

import jade.lang.acl.ACLMessage;
import java.io.Serializable;
import thespian4jade.lang.BinaryMessage;
import thespian4jade.lang.IMessageFactory;

/**
 * An 'Evaluate expression reply' (binary) message.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpressionReplyMessage extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateExpressionReplyMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public int getValue() {
        return value;
    }
    
    public EvaluateExpressionReplyMessage setValue(int value) {
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
    
    public static class Factory implements IMessageFactory<EvaluateExpressionReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public EvaluateExpressionReplyMessage createMessage() {
            return new EvaluateExpressionReplyMessage();
        }        
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
