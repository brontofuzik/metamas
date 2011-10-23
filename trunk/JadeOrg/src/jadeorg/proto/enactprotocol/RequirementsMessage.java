package jadeorg.proto.enactprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import java.util.Arrays;

/**
 * A requirements message parser/generator.
 * A requirements message is a message send from an Organization agent to a Player agent
 * containing information about requirements to enact a certain role.
 * @author Lukáš Kúdela (2011-10-20)
 */
public class RequirementsMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The message template singleton. */
    private MessageTemplate templateSingleton;
    
    /** The message parser singleton. */
    private RequirementsMessageParser parserSingleton;
    
    /** The message generator singleton. */
    private RequirementsMessageGenerator generatorSingleton;

    /** The player AID. */
    private AID player;
    
    private String[] requirements;

    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public MessageTemplate getTemplate() {
        if (templateSingleton == null) {
            templateSingleton = MessageTemplate.and(
                MessageTemplate.MatchProtocol(EnactProtocol.getInstance().getName()),
                MessageTemplate.MatchPerformative(ACLMessage.INFORM));
        }
        return templateSingleton;
    }
    
    /**
     * Gets the message parser singleton.
     * @return the message parser singleton.
     */
    @Override
    public MessageParser getParser() {
        if (parserSingleton == null) {
            parserSingleton = new RequirementsMessageParser();
        }
        return parserSingleton;   
    }

    /**
     * Gets the message genertor singleton.
     * @return the message generator singleton.
     */
    @Override
    public MessageGenerator getGenerator() {
        if (generatorSingleton == null) {
            generatorSingleton = new RequirementsMessageGenerator();
        }
        return generatorSingleton;  
    }
   
    /**
     * Gets the player AID.
     * @return the player AID
     */
    public AID getPlayer() {
        return player;
    }

       /**
     * Sets the player AID.
     * DP: Fluent interface
     * @param player the player AID
     * @return this 'Refuse' message.
     */
    public RequirementsMessage setPlayer(AID player) {
        this.player = player;
        return this;
    }
    
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
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Requirements' message parser.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version 0.1
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
            requirementsMessage.setPlayer(aclMessage.getSender());

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
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version 0.1
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
            aclMessage.addReceiver(requirementsMessage.getPlayer());

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
