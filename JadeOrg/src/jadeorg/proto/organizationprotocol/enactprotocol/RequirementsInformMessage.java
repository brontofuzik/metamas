package jadeorg.proto.organizationprotocol.enactprotocol;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.organizationprotocol.OrganizationMessage;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A 'Requirements' message.
 * A 'Requirements' message is a message send from an organization to a player
 * containing information about requirements to enact a certain role.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public class RequirementsInformMessage extends OrganizationMessage {
    
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
    public RequirementsInformMessage setRequirements(String[] requirements) {
        this.requirements = Arrays.copyOf(requirements, requirements.length);
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
        
    // ---------- PROTECTED ----------
    
    @Override
    protected MessageTemplate createPerformativeTemplate() {
        return MessageTemplate.MatchPerformative(ACLMessage.INFORM);
    }
    
    @Override
    protected MessageGenerator createGenerator() {
        return new RequirementsMessageGenerator();
    }
    
    @Override
    protected MessageParser createParser() {
        return new RequirementsMessageParser();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Requirements' message generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela (2011-10-21)
     * @version %I% %G%
     */
    static class RequirementsMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Generates an ACL message from a 'Requirements inform' message.
         * @param message the 'Requirements' message.
         * @return the ACL message.
         */
        @Override
        public ACLMessage generate(Message message) {           
            RequirementsInformMessage requirementsInformMessage = (RequirementsInformMessage)message;

            // Generate the header.
            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
            aclMessage.setProtocol(EnactRoleProtocol.getInstance().getName());
            aclMessage.addReceiver(requirementsInformMessage.getReceiverPlayer());

            // Generate the content.
            aclMessage.setContent(generateContent(requirementsInformMessage));

            return aclMessage;
        }

        /**
         * Generates the ACL message content.
         * @param requirementsInformMessage the 'Requirements inform' message.
         * @return the ACL message content.
         */
        private String generateContent(RequirementsInformMessage requirementsInformMessage) {
            return String.format("requirements(%1$s)",
                jadeorg.util.StringUtil.join(requirementsInformMessage.getRequirements(), ","));
        }

        // </editor-fold>
    }
    
    /**
     * The 'Requirements' message parser.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version %I% %G%
     */
    static class RequirementsMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private static final Pattern contentPattern = Pattern.compile("requirements\\((.*)\\)");
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Parses the ACL message for a 'Requirements inform' message.
         * @param aclMessage the ACL message.
         * @return the 'Requirements inform' message
         */
        @Override
        public Message parse(ACLMessage aclMessage) {
            RequirementsInformMessage requirementsMessage = new RequirementsInformMessage();

            // Parse the header.
            requirementsMessage.setSenderOrganization(aclMessage.getSender());

            // Parse the content.
            parseContent(requirementsMessage, aclMessage.getContent());

            return requirementsMessage;
        }

        /**
         * Parses the ACL message content.
         */
        private void parseContent(RequirementsInformMessage requirementsInformMessage, String content) {
            Matcher matcher = contentPattern.matcher(content);
            matcher.matches();
            
            String requirementsString = matcher.group(1);
            String[] requirements = !requirementsString.isEmpty() ?
                requirementsString.split(",") :
                new String[0];
            requirementsInformMessage.setRequirements(requirements);
        }

        // </editor-fold>   
    }

    // </editor-fold>
}
