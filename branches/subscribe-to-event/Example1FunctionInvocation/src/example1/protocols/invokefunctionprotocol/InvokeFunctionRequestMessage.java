package example1.protocols.invokefunctionprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An 'Invoke function request' message.
 * @author Lukáš Kúdela
 * @since 2012-01-04
 * @version %I% %G%
 */
public class InvokeFunctionRequestMessage extends TextMessage {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeFunctionRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public int getArgument() {
        return argument;
    }
    
    public void setArgument(int argument) {
        this.argument = argument;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected String generateContent() {
        return String.format("invoke-function(%1$s)", argument);
    }

    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("invoke-function\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        argument = new Integer(matcher.group(1)).intValue();
    }
    
    // </editor-fold>
}
