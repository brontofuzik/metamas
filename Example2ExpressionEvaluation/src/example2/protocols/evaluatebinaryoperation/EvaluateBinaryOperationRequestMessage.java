package example2.protocols.evaluatebinaryoperation;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.language.TextMessage;

/**
 * An 'Evaluate binary operation' (text) message is a message sent
 * by the 'Evaluate binary operation' protocol initiator party (an evaluator)
 * to the responder party (a binary operator) and contains a request to evaluate
 * a binary operation.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateBinaryOperationRequestMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The first operand expression.
     */
    private String operand1;
    
    /**
     * The second operand expression.
     */
    private String operand2;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateBinaryOperationRequestMessage class.
     */
    public EvaluateBinaryOperationRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the first operand expression.
     * @return the first operand expression
     */
    public String getOperand1() {
        return operand1;
    }
    
    /**
     * Sets the first operand expression.
     * @param operand1 the first operand expression
     * @return this 'Evaluate binary operation' message
     * (Design pattern: Fluent interface)
     */
    public EvaluateBinaryOperationRequestMessage setOperand1(String operand1) {
        this.operand1 = operand1;
        return this;
    }
    
    /**
     * Gets the second operand expression.
     * @return the second operand expression
     */
    public String getOperand2() {
        return operand2;
    }
    
    /**
     * Sets the second operand expression.
     * @param operand2 the second expression expression
     * @return this 'Evaluate binary operation' message
     * (Design pattern: Fluent interface)
     */
    public EvaluateBinaryOperationRequestMessage setOperand2(String operand2) {
        this.operand2 = operand2;
        return this;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the ACL message
     */
    @Override
    protected String generateContent() {
        return String.format("evaluate-binary-operation(%1$s,%2$s)", operand1, operand2);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("evaluate-binary-operation\\(([^,]*),(.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        operand1 = matcher.group(1);
        operand2 = matcher.group(2);
    }
    
    // </editor-fold>    
}
