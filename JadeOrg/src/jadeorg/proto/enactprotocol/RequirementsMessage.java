package jadeorg.proto.enactprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.organizationprotocol.OrganizationMessage;
import java.util.Arrays;

/**
 * A 'Requirements' message.
 * A 'Requirements' message is a message send from an organization to a player
 * containing information about requirements to enact a certain role.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public class RequirementsMessage extends OrganizationMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String[] requirements;

    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the requirements.
     * @return the requirements
     */
    public String[] getRequirements() {
        return Arrays.copyOf(requirements, requirements.length);
    }
    
    /**
     * Sets the requirements.
     * @requirements the requirements
     */
    public RequirementsMessage setRequirements(String[] requirements) {
        this.requirements = Arrays.copyOf(requirements, requirements.length);
        return this;
    }
    
    // ---------- PROTECTED ----------
    
    @Override
    protected int getPerformative() {
        return ACLMessage.INFORM;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageParser createParser() {
        return new RequirementsMessageParser();
    }

    @Override
    protected MessageGenerator createGenerator() {
        return new RequirementsMessageGenerator();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Requirements' message parser.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version %I% %G%
     */
    class RequirementsMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Parses the ACL message.
         * @param aclMessage the ACL message.
         * @return the 'Requirements' message.
         */
        @Override
        public Message parse(ACLMessage aclMessage) {
            RequirementsMessage requirementsMessage = new RequirementsMessage();

            // Parse the header.
            requirementsMessage.setSenderOrganization(aclMessage.getSender());

            // Parse the content.
            parseContent(aclMessage.getContent(), requirementsMessage);

            return requirementsMessage;
        }

        /**
         * Parses the content of the ACL message.
         */
        private void parseContent(String content, RequirementsMessage requirementsMessage) {
            // TODO
        }

        // </editor-fold>   
    }
    
    /**
     * The 'Requirements' message generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela (2011-10-21)
     * @version %I% %G%
     */
    class RequirementsMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Generates an ACL message from a 'Requirements' message.
         * @param message the 'Requirements' message.
         * @return the ACL message.
         */
        @Override
        public ACLMessage generate(Message message) {
            RequirementsMessage requirementsMessage = (RequirementsMessage)message;

            // Generate the ACL message header.
            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
            aclMessage.setProtocol(EnactProtocol.getInstance().getName());
            aclMessage.addReceiver(requirementsMessage.getReceiverPlayer());

            // Generate the ACL message content.
            aclMessage.setContent(generateContent(requirementsMessage));

            return aclMessage;
        }

        /**
         * Generates the ACL message content from a 'Requirements' message.
         * @param requirementsMessage the 'Requirements' message.
         * @return the ACL message content.
         */
        private String generateContent(RequirementsMessage requirementsMessage) {
            // TODO
            return "";
        }

        // </editor-fold>
    }

    // </editor-fold>
}
