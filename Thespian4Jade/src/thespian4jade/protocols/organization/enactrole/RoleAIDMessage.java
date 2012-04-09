package thespian4jade.protocols.organization.enactrole;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.language.IMessageFactory;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Role AID' message is send by an Organization agent to a Player agent
 * as part the 'Enact' protocol and contains information about the Role agent's AID.
 * @author Lukáš Kúdela
 * @since 2011-10-23
 * @version %I% %G%
 */
public class RoleAIDMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The role AID.
     */
    private AID roleAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the RoleAIDMessage class.
     * The corresponding ACL message has the INFORM performative.
     */
    public RoleAIDMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the role AID.
     * @return the role AID
     */
    public AID getRoleAID() {
        return roleAID;
    }
    
    /**
     * Sets the role AID.
     * @param roleAID the role AID
     * @return this 'Role AID' message (Design pattern: Fleunt interface)
     */
    public RoleAIDMessage setRoleAID(AID roleAID) {
        this.roleAID = roleAID;
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
        return String.format("role-aid(%1$s)", roleAID.getName());
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("role-aid\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();

        String roleAID = matcher.group(1);
        this.roleAID = new AID(roleAID, true);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * A 'Role AID' message factory.
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */ 
    public static class Factory implements IMessageFactory<RoleAIDMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Role AID' message.
         * @return an empty 'Role AID' message
         */
        @Override
        public RoleAIDMessage createMessage() {
            return new RoleAIDMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}