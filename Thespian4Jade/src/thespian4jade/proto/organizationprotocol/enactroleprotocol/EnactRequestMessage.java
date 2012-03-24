package thespian4jade.proto.organizationprotocol.enactroleprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An 'Enact request' message.
 * An 'Enact request' message is a message send from a player to an organization
 * containing the request to enact a role.
 * @author Lukáš Kúdela
 * @since 2011-11-05
 * @version %I% %G%
 */
public class EnactRequestMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String roleName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EnactRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getRoleName() {
        return roleName;
    }
    
    public EnactRequestMessage setRoleName(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        this.roleName = roleName;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public String generateContent() {
        return String.format("enact-role(%1$s)", roleName);
    }

    @Override
    public void parseContent(String content) {
        final Pattern contentPattern = Pattern.compile("enact-role\\((.*)\\)");
        Matcher matcher = contentPattern.matcher(content);
        matcher.matches();
 
        roleName = matcher.group(1);
    }
    
    // </editor-fold>
}