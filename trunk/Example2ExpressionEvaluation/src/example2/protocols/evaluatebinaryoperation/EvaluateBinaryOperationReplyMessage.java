package example2.protocols.evaluatebinaryoperation;

import jade.lang.acl.ACLMessage;
import java.io.Serializable;
import thespian4jade.language.BinaryMessage;
import thespian4jade.language.IMessageFactory;

/**
 * An 'Evaluate binary operation reply' (binary) message is a message sent by
 * the 'Evaluate binary operation' protocol responder party (a binary operator)
 * to the initiator party (an evaluator) and contains the result.
 * of the evaluated binary operation.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateBinaryOperationReplyMessage extends BinaryMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The operation result.
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
     * Gets the operation result.
     * @return the operation result
     */
    public int getResult() {
        return result;
    }
    
    /**
     * Sets the operation result.
     * @param result the operation result
     * @return this 'Evaluate binary operation reply' message
     * (Design pattern: Fluent interface)
     */
    public EvaluateBinaryOperationReplyMessage setResult(int result) {
        this.result = result;
        return this;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Gets the content object for the ACL message.
     * @return the content object for the ACL message
     */
    @Override
    protected Serializable getContentObject() {
        return new Integer(result);
    }

    /**
     * Sets the content object from the ACL message.
     * @param contentObject the content object from the ACL message
     */
    @Override
    protected void setContentObject(Serializable contentObject) {
        result = ((Integer)contentObject).intValue();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * An 'Evaluate binary operation reply' message factory.
     */
    public static class Factory implements IMessageFactory<EvaluateBinaryOperationReplyMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public EvaluateBinaryOperationReplyMessage createMessage() {
            return new EvaluateBinaryOperationReplyMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
