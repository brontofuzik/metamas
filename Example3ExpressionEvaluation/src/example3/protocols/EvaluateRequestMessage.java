package example3.protocols;

import jade.lang.acl.ACLMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.lang.TextMessage;

/**
 * An 'Evaluate request' (text) message.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateRequestMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String operand1;
    
    private String operand2;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getOperand1() {
        return operand1;
    }
    
    public EvaluateRequestMessage setOperand1(String operand1) {
        this.operand1 = operand1;
        return this;
    }
    
    public String getOperand2() {
        return operand2;
    }
    
    public EvaluateRequestMessage setOperand2(String operand2) {
        this.operand2 = operand2;
        return this;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected String generateContent() {
        return String.format("evaluate(%1$s,%2$s)", operand1, operand2);
    }

    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("evaluate\\(([^,]*),([^)]*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        operand1 = matcher.group(1);
        operand2 = matcher.group(2);
    }
    
    // </editor-fold>    
}
