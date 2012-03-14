package example3.protocols.evaluateexpression;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.lang.TextMessage;

/**
 * A 'Request' (text) message.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class RequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String expression;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getExpression() {
        return expression;
    }
    
    public RequestMessage setExpression(String expression) {
        this.expression = expression;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected String generateContent() {
        return String.format("evaluate(%1$s)", expression);
    }

    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("evaluate\\(([^)]*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        expression = matcher.group(1);
    }
    
    // </editor-fold>
}
