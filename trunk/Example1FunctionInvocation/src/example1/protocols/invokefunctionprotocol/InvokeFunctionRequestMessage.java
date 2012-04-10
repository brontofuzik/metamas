package example1.protocols.invokefunctionprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An 'Invoke function request' message is a message sent by the 'Invoke function'
 * protocol initiator party (an invoker) to the responder party (a executor) and
 * contains a request to invoke the function for a particular argument.
 * @author Lukáš Kúdela
 * @since 2012-01-04
 * @version %I% %G%
 */
public class InvokeFunctionRequestMessage extends TextMessage {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The function rgument.
     */
    private int argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeFunctionRequestMessage class.
     */
    public InvokeFunctionRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the function argument.
     * @return the function argument
     */
    public int getArgument() {
        return argument;
    }
    
    /**
     * Sets the function argument
     * @param argument the function argument
     */
    public void setArgument(int argument) {
        this.argument = argument;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the ACL message
     */
    @Override
    protected String generateContent() {
        return String.format("invoke-function(%1$s)", argument);
    }
    
    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    protected void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("invoke-function\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        argument = new Integer(matcher.group(1)).intValue();
    }
    
    // </editor-fold>
}
