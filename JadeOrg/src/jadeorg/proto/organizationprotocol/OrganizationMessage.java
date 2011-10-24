package jadeorg.proto.organizationprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.lang.PlayerMessage;
import java.util.StringTokenizer;

/**
 * An organization message.
 * An organization message is a message send from a Player agent to an Organization agent
 * containing a request to enact/deact a certain role.
 * @author Lukáš Kúdela (2011-10-20)
 * @version 0.1
 */
public class OrganizationMessage extends PlayerMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String action = "";

    private String role = "";

    // </editor-fold>
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
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
    
    // ---------- PROTECTED ----------
    
    @Override
    protected int getPerformative() {
        return ACLMessage.REQUEST;
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageParser createParser() {
        return new OrganizationMessageParser();
    }

    @Override
    protected MessageGenerator createGenerator() {
        return new OrganizationMessageGenerator();
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
