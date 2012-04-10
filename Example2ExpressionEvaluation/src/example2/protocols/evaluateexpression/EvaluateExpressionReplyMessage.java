package example2.protocols.evaluateexpression;

import jade.lang.acl.ACLMessage;
import java.io.Serializable;
import thespian4jade.language.BinaryMessage;
import thespian4jade.language.IMessageFactory;

/**
 * An 'Evaluate expression reply' (binary) message is a message sent by
 * the 'Evaluate expression' protocol responder party (an evaluator)
 * to the initiator party (a binary operator) and contains the value
 * of the evaluated expression.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpressionReplyMessage extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The expression value.
     */
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateExpressionReplyMessage class.
     */
    public EvaluateExpressionReplyMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the expression value.
     * @return the expression value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Sets the expression value.
     * @param value the expression value
     * @return this 'Evaluate expression reply' message
     * (Design pattern: Fluent interface)
     */
    public EvaluateExpressionReplyMessage setValue(int value) {
        this.value = value;
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
        return new Integer(value);
    }

    /**
     * Sets the content object from the ACL message.
     * @param contentObject the content object from the ACL message
     */
    @Override
    protected void setContentObject(Serializable contentObject) {
        value = ((Integer)contentObject).intValue();
    }
 
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * An 'Evaluate expression reply' message factory.
     */
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
