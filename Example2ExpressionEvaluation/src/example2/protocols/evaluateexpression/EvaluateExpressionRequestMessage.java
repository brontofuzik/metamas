package example2.protocols.evaluateexpression;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.language.TextMessage;

/**
 * A 'Evaluate expression request' (text) message.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateExpressionRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String expression;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateExpressionRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getExpression() {
        return expression;
    }
    
    public EvaluateExpressionRequestMessage setExpression(String expression) {
        this.expression = expression;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected String generateContent() {
        return String.format("evaluate-expression(%1$s)", expression);
    }

    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("evaluate-expression\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        expression = matcher.group(1);
    }
    
    // </editor-fold>
}
