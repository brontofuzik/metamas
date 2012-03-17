package example2.protocols;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.lang.TextMessage;

/**
 * An 'Evaluate binary operation request' (text) message.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateBinaryOperationRequestMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The first operand.
     */
    private String operand1;
    
    /**
     * The second operand.
     */
    private String operand2;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateBinaryOperationRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the first operand.
     * @return the first operand
     */
    public String getOperand1() {
        return operand1;
    }
    
    /**
     * Sets the first operand.
     * @param operand1 the first operand
     * @return this 'Evaluate binary operation' message
     */
    public EvaluateBinaryOperationRequestMessage setOperand1(String operand1) {
        this.operand1 = operand1;
        return this;
    }
    
    /**
     * Gets the second operand.
     * @return the second operand
     */
    public String getOperand2() {
        return operand2;
    }
    
    /**
     * Sets the second operand.
     * @param operand2 the second expression
     * @return this 'Evaluate binary operation'
     */
    public EvaluateBinaryOperationRequestMessage setOperand2(String operand2) {
        this.operand2 = operand2;
        return this;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected String generateContent() {
        return String.format("evaluate-binary-operation(%1$s,%2$s)", operand1, operand2);
    }

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
