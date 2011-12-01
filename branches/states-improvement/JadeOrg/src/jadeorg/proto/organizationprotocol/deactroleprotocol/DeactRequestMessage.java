/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.organizationprotocol.deactroleprotocol;

import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRequestMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.organizationprotocol.OrganizationMessage;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;

/**
 * A 'Deact request' message.
 * A 'Deact request' message is a message send from a player to an organization
 * containing the request to deact a role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class DeactRequestMessage extends OrganizationMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String roleName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getRoleName() {
        return roleName;
    }
    
    public DeactRequestMessage setRoleName(String roleName) {
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
    protected MessageParser createParser() {
        return new DeactRequestMessageParser();
    }
    
    @Override
    protected MessageGenerator createGenerator() {
        return new DeactRequestMessageGenerator();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Deact request' message parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela (2011-11-06)
     * @version %I% %G%
     */
    class DeactRequestMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Parses the ACL message.
         * @param aclMessage the ACL message
         * @return the 'Enact request' message
         */
        @Override
        public Message parse(ACLMessage aclMessage) {
            EnactRequestMessage deactRequestMessage = new EnactRequestMessage();

            // Parse the header.
            deactRequestMessage.setSenderPlayer(aclMessage.getSender());

            // Parse the content.
            parseContent(aclMessage.getContent(), deactRequestMessage);

            return deactRequestMessage;
        }
        
        /**
         * Parses the content of the ACL message.
         */
        private void parseContent(String content, EnactRequestMessage deactRequestMessage) {
            // TODO
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Deact request' message generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela (2011-11-06)
     * @version %I% %G%
     */
    class DeactRequestMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Generates an ACL message from a 'Deact request' message.
         * @param message the 'Deact request' message
         * @return the ACL message
         */
        @Override
        public ACLMessage generate(Message message) {
           EnactRequestMessage deactRequestMessage = (EnactRequestMessage)message;

            // Generate the ACL message header.
            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
            aclMessage.setProtocol(DeactRoleProtocol.getInstance().getName());
            aclMessage.addReceiver(deactRequestMessage.getReceiverOrganization());

            // Generate the ACL message content.
            aclMessage.setContent(generateContent(deactRequestMessage));

            return aclMessage;
        }
        
        /**
         * Generates the ACL message content from a 'Deact request' message.
         * @param requirementsMessage the 'Deact request' message
         * @return the ACL message content
         */
        private String generateContent(EnactRequestMessage deactRequestMessage) {
            // TODO
            return "";
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}