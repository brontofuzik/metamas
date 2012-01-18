package example1.protocols.calculatefactorialprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Calculate factorial request' message.
 * @author Lukáš Kúdela
 * @since 2012-01-04
 * @version %I% %G%
 */
public class RequestMessage extends TextMessage {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RequestMessage() {
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
        return String.format("calculate-factorial(%1$s)", argument);
    }

    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("calculate-factorial\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        argument = new Integer(matcher.group(1)).intValue();
    }
    
    // </editor-fold>
}
