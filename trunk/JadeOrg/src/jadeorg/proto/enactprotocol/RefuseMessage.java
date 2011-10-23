package jadeorg.proto.enactprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;

/**
 * A refuse message parser/generator.
 * A refuse message is a message send from an Organization agent to a Player agent
 * containing a refusal to enact/deact a certain role.
 * @author Lukáš Kúdela (2011-10-21)
 */
public class RefuseMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The message template sinleton. */
    private MessageTemplate templateSingleton;
    
    /** The message parser singleton. */
    private RefuseMessageParser parserSingleton;
    
    /** The message generator singleton. */
    private RefuseMessageGenerator generatorSingleton;
    
    /** The player AID. */
    private AID player;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public MessageTemplate getTemplate() {
        if (templateSingleton == null) {
            templateSingleton = MessageTemplate.and(
                MessageTemplate.MatchProtocol(EnactProtocol.getInstance().getName()),
                MessageTemplate.MatchPerformative(ACLMessage.REFUSE));
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
            parserSingleton = new RefuseMessageParser();
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
            generatorSingleton = new RefuseMessageGenerator();
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
    public RefuseMessage setPlayer(AID player) {
        this.player = player;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A 'Refuse' message parser.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version 0.1
     */
    class RefuseMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public Message parse(ACLMessage aclMessage) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private void parseContent(ACLMessage aclMessage, RefuseMessage refuseMessage) {
            // TODO
        }

        // </editor-fold>    
    }
    
    /**
     * A 'Refuse' message generator.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version 0.1
     */
    class RefuseMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public ACLMessage generate(Message message) {
            RefuseMessage refuseMessage = (RefuseMessage)message;

            // Generate the message header.
            ACLMessage aclMessage = new ACLMessage(ACLMessage.REFUSE);
            aclMessage.setProtocol(EnactProtocol.getInstance().getName());
            aclMessage.addReceiver(refuseMessage.getPlayer());

            // Generate the content.
            aclMessage.setContent(generateContent(refuseMessage));

            return aclMessage;
        }

        private String generateContent(RefuseMessage refuseMessage) {
            return "refuse";
        }

        // </editor-fold>    
    }
    
    // </editor-fold>
}
