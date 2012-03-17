package example2.protocols;

import jade.lang.acl.ACLMessage;
import java.io.Serializable;
import thespian4jade.lang.BinaryMessage;
import thespian4jade.lang.MessageFactory;

/**
 * A 'Evaluate binary operation reply' (binary) message.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateBinaryOperationReplyMessage extends BinaryMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The result.
     */
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateBinaryOperationReplyMessage class.
     */
    public EvaluateBinaryOperationReplyMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the result.
     * @return the result
     */
    public int getResult() {
        return result;
    }
    
    /**
     * Sets the result.
     * @param result the result
     * @return this 'Evaluate binary operation reply' message
     */
    public EvaluateBinaryOperationReplyMessage setResult(int result) {
        this.result = result;
        return this;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected Serializable getContentObject() {
        return new Integer(result);
    }

    @Override
    protected void setContentObject(Serializable contentObject) {
        result = ((Integer)contentObject).intValue();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    public static class Factory implements MessageFactory<EvaluateBinaryOperationReplyMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public EvaluateBinaryOperationReplyMessage createMessage() {
            return new EvaluateBinaryOperationReplyMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
