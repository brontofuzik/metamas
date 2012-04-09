package thespian4jade.protocols.role.invokecompetence;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A 'Invoke competence request' (text) message is sent by the 'Invoke competence'
 * initiator party (a player) to the responder party (a role) and contains
 * a request to invoke a particular competence.
 * @author Lukáš Kúdela
 * @since 2011-11-09
 * @version %I% %G%
 */
public class InvokeCompetenceRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The name of the competence to invoke.
     */
    private String competenceName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeCompetenceRequestMessage class.
     * The corresponding ACL message has the REQUEST performative.
     */
    public InvokeCompetenceRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the name competence to invoke.
     * @return the name of the competence
     */
    public String getCompetenceName() {
        return competenceName;
    }
    
    /**
     * Sets the name of the competence to invoke.
     * @param competenceName the name of the competence
     * @return this 'Invoke competence request' message
     * (Design pattern: Fluent interface)
     */
    public InvokeCompetenceRequestMessage setCompetence(String competenceName) {
        this.competenceName = competenceName;
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
        return String.format("invoke-competence(%1$s)", competenceName);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("invoke-competence\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        competenceName = matcher.group(1);
    }
     
    // </editor-fold>
}
