package thespian4jade.proto.roleprotocol.invokecompetenceprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A 'Invoke competence request' message.
 * @author Lukáš Kúdela
 * @since 2011-11-09
 * @version %I% %G%
 */
public class InvokeCompetenceRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String competence;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeCompetenceRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public String getCompetence() {
        return competence;
    }
    
    public InvokeCompetenceRequestMessage setCompetence(String competenceName) {
        this.competence = competenceName;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public String generateContent() {
        return String.format("invoke-competence(%1$s)", competence);
    }

    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("invoke-competence\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        competence = matcher.group(1);
    }
     
    // </editor-fold>
}
