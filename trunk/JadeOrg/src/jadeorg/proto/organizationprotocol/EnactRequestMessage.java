package jadeorg.proto.organizationprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.organizationprotocol.enactprotocol.EnactProtocol;

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
    
    // ---------- PROTECTED ----------
    
    @Override
    protected int getPerformative() {
        return ACLMessage.REQUEST;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageParser createParser() {
        return new EnactRequestMessageParser();
    }
    
    @Override
    protected MessageGenerator createGenerator() {
        return new EnactRequestMessageGenerator();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Enact request' message parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela (2011-11-05)
     * @version %I% %G%
     */
    class EnactRequestMessageParser extends MessageParser {

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
            parseContent(aclMessage.getContent(), enactRequestMessage);

            return enactRequestMessage;
        }
        
        /**
         * Parses the content of the ACL message.
         */
        private void parseContent(String content, EnactRequestMessage enactRequestMessage) {
            // TODO
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Enact request' message generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela (2011-11-05)
     * @version %I% %G%
     */
    class EnactRequestMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Generates an ACL message from a 'Enact request' message.
         * @param message the 'Enact request' message
         * @return the ACL message
         */
        @Override
        public ACLMessage generate(Message message) {
           EnactRequestMessage enactRequestMessage = (EnactRequestMessage)message;

            // Generate the ACL message header.
            ACLMessage aclMessage = new ACLMessage(enactRequestMessage.getPerformative());
            aclMessage.setProtocol(EnactProtocol.getInstance().getName());
            aclMessage.addReceiver(enactRequestMessage.getReceiverOrganization());

            // Generate the ACL message content.
            aclMessage.setContent(generateContent(enactRequestMessage));

            return aclMessage;
        }
        
        /**
         * Generates the ACL message content from a 'Enact request' message.
         * @param requirementsMessage the 'Enact request' message
         * @return the ACL message content
         */
        private String generateContent(EnactRequestMessage enactRequestMessage) {
            // TODO
            return "";
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}