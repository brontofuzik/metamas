package jadeorg.proto.enactprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;

/**
 * A 'Role AID' message.
 * A 'Role AID' message is send by an Organization agent to a Player agent
 * as part the 'Enact' protocol and contains information about the Role agent's AID.
 * @author Lukáš Kúdela (2011-10-23)
 * @version 0.1
 */
public class RoleAIDMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private MessageTemplate templateSingleton;
    
    private RoleAIDMessageParser parserSingleton;
    
    private RoleAIDMessageGenerator generatorSingleton;
    
    private AID player;
    
    private AID roleAID;
    
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

    @Override
    public MessageParser getParser() {
        if (parserSingleton == null) {
            parserSingleton = new RoleAIDMessageParser();
        }
        return parserSingleton;
    }

    @Override
    public MessageGenerator getGenerator() {
        if (generatorSingleton == null) {
            generatorSingleton = new RoleAIDMessageGenerator();
        }
        return generatorSingleton;
    }
    
    public AID getPlayer() {
        return player;
    }
    
    public RoleAIDMessage setPlayer(AID player) {
        this.player = player;
        return this;
    }
    
    public AID getRoleAID() {
        return roleAID;
    }
    
    public RoleAIDMessage setRoleAID(AID roleAID) {
        this.roleAID = roleAID;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    class RoleAIDMessageParser extends MessageParser {

        @Override
        public Message parse(ACLMessage aclMessage) {
            // TODO
            RoleAIDMessage roleAIDMessage = new RoleAIDMessage();
            
            // Parse the content.
            parseContent(aclMessage.getContent(), roleAIDMessage);
            
            return roleAIDMessage;
        }
        
        // ---------- PRIVATE ----------
        
        private void parseContent(String content, RoleAIDMessage roleAIDMessage) {
            roleAIDMessage.setRoleAID(new AID(content, true));
        }
    }
    
    class RoleAIDMessageGenerator extends MessageGenerator {

        @Override
        public ACLMessage generate(Message message) {
            RoleAIDMessage roleAIDMessage = (RoleAIDMessage)message;
            
            ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
            aclMessage.setProtocol(roleAIDMessage.getProtocol().getName());
            
            // Generate the content.
            aclMessage.setContent(generateContent(roleAIDMessage));
            
            return aclMessage;
        }
        
        // ---------- PRIVATE ----------

        private String generateContent(RoleAIDMessage roleAIDMessage) {
            return roleAIDMessage.toString();
        }
    }
    
    // </editor-fold>
}
