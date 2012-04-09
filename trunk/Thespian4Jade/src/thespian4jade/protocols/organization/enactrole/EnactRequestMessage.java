package thespian4jade.protocols.organization.enactrole;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An 'Enact request' message is a message send from a player to an organization
 * containing the request to enact a role.
 * @author Lukáš Kúdela
 * @since 2011-11-05
 * @version %I% %G%
 */
public class EnactRequestMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The name of the role to deact.
     */
    private String roleName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EnactRequestMessage class.
     * The corresponding ACL message has the REQUEST performative.
     */
    public EnactRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the role to enact.
     * @return the name of the role
     */
    public String getRoleName() {
        return roleName;
    }
    
    /**
     * Sets the name of the role to enact.
     * @param roleName the name of the role
     * @return this 'Enact request' message (Design pattern: Fluent interface)
     */
    public EnactRequestMessage setRoleName(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        this.roleName = roleName;
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
        return String.format("enact-role(%1$s)", roleName);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("enact-role\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        roleName = matcher.group(1);
    }
    
    // </editor-fold>
}