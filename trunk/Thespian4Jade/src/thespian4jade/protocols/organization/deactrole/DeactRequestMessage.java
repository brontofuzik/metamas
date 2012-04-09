package thespian4jade.protocols.organization.deactrole;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Deact request' message is a message send from a player to an organization
 * containing the request to deact a role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class DeactRequestMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The name of the role to enact.
     */
    private String roleName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the DeactRequestMessage class.
     * The corresponding ACL message has the REQUEST performative.
     */
    public DeactRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the role to deact.
     * @return the name of the role
     */
    public String getRoleName() {
        return roleName;
    }
    
    /**
     * Sets the name of the role to deact.
     * @param roleName the name of the role
     * @return this 'Deact request' message (Design pattern: Fluent interface)
     */
    public DeactRequestMessage setRoleName(String roleName) {
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
        return String.format("deact-role(%1$s)", roleName);
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("deact-role\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        this.roleName = matcher.group(1);
    }
    
    // </editor-fold>
}