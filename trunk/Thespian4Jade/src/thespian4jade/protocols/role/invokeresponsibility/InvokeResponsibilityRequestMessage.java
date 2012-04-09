package thespian4jade.protocols.role.invokeresponsibility;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Invoke responsibility request' (text) message is sent by the 'Invoke responsibility'
 * initiator party (a role) to the responder party (a player) and contains
 * a request to invoke a particular responsibility.
 * @author Lukáš Kúdela
 * @since 2011-11-09
 * @version %I% %G%
 */
public class InvokeResponsibilityRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The name of the responsibility to invoke.
     */
    private String responsibilityName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeResponsibilityRequestMessage class.
     * The corresponding ACL message has the REQUEST performative.
     */
    public InvokeResponsibilityRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
   
    /**
     * Gets the name responsibility to invoke.
     * @return the name of the responsibility
     */
    public String getResponsibility() {
        return responsibilityName;
    }
 
    /**
     * Sets the name of the responsibility to invoke.
     * @param responsibilityName the name of the responsibility
     * @return this 'Invoke responsibility request' message
     * (Design pattern: Fluent interface)
     */
    public InvokeResponsibilityRequestMessage setResponsibility(String responsibilityName) {
        this.responsibilityName = responsibilityName;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the ACL message
     */
    @Override
    public String generateContent() {
        return String.format("invoke-responsibility(%1$s)", responsibilityName);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("invoke-responsibility\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        responsibilityName = matcher.group(1);
    }
    
    // </editor-fold>
}
