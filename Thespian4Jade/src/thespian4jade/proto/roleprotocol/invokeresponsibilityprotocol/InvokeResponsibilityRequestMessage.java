package thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Responsibility request' message.
 * A 'Responsibility request' message is a message sent from a role to a player
 * containing a request to invoke responsibility.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class InvokeResponsibilityRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String responsibility;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeResponsibilityRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getResponsibility() {
        return responsibility;
    }
    
    public InvokeResponsibilityRequestMessage setResponsibility(String responsibility) {
        this.responsibility = responsibility;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        return String.format("invoke-responsibility(%1$s)", responsibility);
    }

    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("invoke-responsibility\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        responsibility = matcher.group(1);
    }
    
    // </editor-fold>
}
