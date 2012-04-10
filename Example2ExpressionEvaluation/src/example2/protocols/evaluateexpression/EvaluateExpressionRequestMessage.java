package example2.protocols.evaluateexpression;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.language.TextMessage;

/**
 * An 'Evaluate expression request' (text) message is a message sent
 * by the 'Evaluate expression' protocol initiator party (a binary operator)
 * to the responder party (an evaluator) and contains a request to evaluate
 * an expression.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpressionRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The expression.
     */
    private String expression;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateExpressionRequestMessage class.
     */
    public EvaluateExpressionRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the expression.
     * @return the expression
     */
    public String getExpression() {
        return expression;
    }
    
    /**
     * Sets the expression.
     * @param expression the expression
     * @return this 'Evaluate expression request' messge
     * (Design pattern: Fluent interface)
     */
    public EvaluateExpressionRequestMessage setExpression(String expression) {
        this.expression = expression;
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
        return String.format("evaluate-expression(%1$s)", expression);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("evaluate-expression\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        expression = matcher.group(1);
    }
    
    // </editor-fold>
}
