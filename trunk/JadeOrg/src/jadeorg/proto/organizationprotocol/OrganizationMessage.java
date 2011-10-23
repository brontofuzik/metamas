package jadeorg.proto.organizationprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import java.util.StringTokenizer;

/**
 * An organization message.
 * An organization message is a message send from a Player agent to an Organization agent
 * containing a request to enact/deact a certain role.
 * @author Lukáš Kúdela (2011-10-20)
 * @version 0.1
 */
public class OrganizationMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The message template singleton. */
    private MessageTemplate templateSingleton;
    
    /** The message parser singleton. */
    private OrganizationMessageParser parserSingleton;
    
    /** The message generator singleton. */
    private OrganizationMessageGenerator generatorSingleton;
    
    /** The player AID. */
    private AID player;
    
    private String action = "";

    private String role = "";

    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the template to receive this message.
     * @return the template to receive this message. 
     */
    @Override
    public MessageTemplate getTemplate() {
        if (templateSingleton == null) {
            templateSingleton = MessageTemplate.and(
            MessageTemplate.MatchProtocol(OrganizationProtocol.getInstance().getName()),
            MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
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
            parserSingleton = new OrganizationMessageParser();
        }
        return parserSingleton;
    }

    /**
     * Gets the message parser singleton.
     * @return the message generator singleton.
     */
    @Override
    public MessageGenerator getGenerator() {
        if (generatorSingleton == null) {
            generatorSingleton = new OrganizationMessageGenerator();
        }
        return generatorSingleton;
    }
    
    /**
     * Gets the player AID.
     * @return the player AID.
     */
    public AID getPlayer() {
        return player;
    }
    
    /**
     * Sets the player ADI.
     * DP: Fluent iterface
     * @param player the player AID.
     * @return this 'Organization' message.
     */
    public OrganizationMessage setPlayer(AID player) {
        this.player = player;
        return this;
    }
    
    public String getAction() {
        return action;
    }
    
    public OrganizationMessage setAction(String action) {
        this.action = action;
        return this;
    }

    public String getRole() {
        return role;
    }
    
    public OrganizationMessage setRole(String role) {
        this.role = role;
        return this;
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Organization' message parser.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version 0.1
     */
    class OrganizationMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public Message parse(ACLMessage aclMessage) {
            OrganizationMessage organizationMessage = new OrganizationMessage();

            // Parse the player.
            organizationMessage.setPlayer(aclMessage.getSender());

            // Parse the content.
            parseContent(aclMessage.getContent(), organizationMessage);

            return organizationMessage;
        }
        
        // ---------- PRIVATE ----------

        private void parseContent(String content, OrganizationMessage organiationMessage) {
            StringTokenizer tokenizer = new StringTokenizer(content);

            // Parse the action.
            if (tokenizer.hasMoreTokens()) {
                organiationMessage.setAction(tokenizer.nextToken());
            }

            // Parse the role.
            if (tokenizer.hasMoreTokens()) {
                organiationMessage.setRole(tokenizer.nextToken());
            }
        }

        // </editor-fold>
    }
    
    /**
     * The 'Organization' message generator.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version 0.1
     */
    class OrganizationMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public ACLMessage generate(Message message) {
            OrganizationMessage organizationMessage = (OrganizationMessage)message;

            ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
            aclMessage.setContent(generateContent(organizationMessage));
            return aclMessage;
        }

        // ---------- PRIVATE ----------
        
        private String generateContent(OrganizationMessage organizationMessage) {
            return "";
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
