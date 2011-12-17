package jadeorg.proto.organizationprotocol.enactroleprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.organizationprotocol.OrganizationMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Role AID' message.
 * A 'Role AID' message is send by an Organization agent to a Player agent
 * as part the 'Enact' protocol and contains information about the Role agent's AID.
 * @author Lukáš Kúdela
 * @since 2011-10-23
 * @version %I% %G%
 */
public class RoleAIDMessage extends OrganizationMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID roleAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public AID getRoleAID() {
        return roleAID;
    }
    
    public RoleAIDMessage setRoleAID(AID roleAID) {
        this.roleAID = roleAID;
        return this;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageTemplate createPerformativeTemplate() {
        return MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    }

    @Override
    protected MessageGenerator createGenerator() {
        return new RoleAIDMessageGenerator();
    }
    
    @Override
    protected MessageParser createParser() {
        return new RoleAIDMessageParser();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    static class RoleAIDMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public ACLMessage generate(Message message) {
            RoleAIDMessage roleAIDMessage = (RoleAIDMessage)message;
            
            // Generate the header.
            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
            // TAG SETTING-PROTOCOL
            //aclMessage.setProtocol(EnactRoleProtocol.getInstance().getName());
            aclMessage.addReceiver(roleAIDMessage.getReceiverPlayer());
            
            // Generate the content.
            aclMessage.setContent(generateContent(roleAIDMessage));
            
            return aclMessage;
        }
        
        // ---------- PRIVATE ----------

        private String generateContent(RoleAIDMessage roleAIDMessage) {
            return String.format("role-aid(%1$s)", roleAIDMessage.getRoleAID().getName());
        }
        
        // </editor-fold>
    }
    
    static class RoleAIDMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private static final Pattern contentPattern = Pattern.compile("role-aid\\((.*)\\)");
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public Message parse(ACLMessage aclMessage) {
            // TODO
            RoleAIDMessage roleAIDMessage = new RoleAIDMessage();
            
            // Parse the content.
            parseContent(roleAIDMessage, aclMessage.getContent());
            
            return roleAIDMessage;
        }
        
        // ---------- PRIVATE ----------
        
        private void parseContent(RoleAIDMessage roleAIDMessage, String content) {
            Matcher matcher = contentPattern.matcher(content);
            matcher.matches();
            
            String roleAID = matcher.group(1);
            roleAIDMessage.setRoleAID(new AID(roleAID, true));
        }
        
        // </editor-fold> 
    }
        
    // </editor-fold>
}
