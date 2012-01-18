package jadeorg.proto.roleprotocol.invokerequirementprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Requirement request' message.
 * A 'Requirement request' message is a message sent from a role to a player
 * containing ... TODO Finish.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class InvokeRequirementRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String requirement;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeRequirementRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getRequirement() {
        return requirement;
    }
    
    public InvokeRequirementRequestMessage setRequirement(String requirement) {
        this.requirement = requirement;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        return String.format("invoke-requirement(%1$s)", requirement);
    }

    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("invoke-requirement\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        requirement = matcher.group(1);
    }
    
    // </editor-fold>
}
