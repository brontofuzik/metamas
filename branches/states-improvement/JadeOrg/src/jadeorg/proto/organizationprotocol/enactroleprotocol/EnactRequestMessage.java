package jadeorg.proto.organizationprotocol.enactroleprotocol;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.organizationprotocol.OrganizationMessage;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
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
public class EnactRequestMessage extends OrganizationMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String roleName;
    
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
    protected MessageTemplate createPerformativeTemplate() {
        return MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
    }
    
    @Override
    protected MessageGenerator createGenerator() {
        return new EnactRequestMessageGenerator();
    }
    
    @Override
    protected MessageParser createParser() {
        return new EnactRequestMessageParser();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Enact request' message generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela (2011-11-05)
     * @version %I% %G%
     */
    private static class EnactRequestMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Generates an ACL message from a 'Enact request' message.
         * @param message the 'Enact request' message
         * @return the ACL message
         */
        @Override
        public ACLMessage generate(Message message) {
            EnactRequestMessage enactRequestMessage = (EnactRequestMessage)message;

            // Generate the header.
            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
            aclMessage.setProtocol(EnactRoleProtocol.getInstance().getName());
            aclMessage.addReceiver(enactRequestMessage.getReceiverOrganization());

            // Generate the content.
            aclMessage.setContent(generateContent(enactRequestMessage));

            return aclMessage;
        }
        
        // ---------- PRIVATE ----------
        
        private String generateContent(EnactRequestMessage enactRequestMessage) {
            return String.format("enact(%1$s)", enactRequestMessage.getRoleName());
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Enact request' message parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela (2011-11-05)
     * @version %I% %G%
     */
    private static class EnactRequestMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private static final Pattern contentPattern = Pattern.compile("enact\\((.*)\\)");
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Parses the ACL message.
         * @param aclMessage the ACL message
         * @return the 'Enact request' message
         */
        @Override
        public Message parse(ACLMessage aclMessage) {
            EnactRequestMessage enactRequestMessage = new EnactRequestMessage();

            // Parse the header.
            enactRequestMessage.setSenderPlayer(aclMessage.getSender());

            // Parse the content.
            parseContent(enactRequestMessage, aclMessage.getContent());

            return enactRequestMessage;
        }
        
        // ---------- PRIVATE ----------
        
        private void parseContent(EnactRequestMessage enactRequestMessage, String content) {
            Matcher matcher = contentPattern.matcher(content);
            matcher.matches();
            
            String roleName = matcher.group(1);
            enactRequestMessage.setRoleName(roleName);
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}