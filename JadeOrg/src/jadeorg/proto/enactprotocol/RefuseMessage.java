package jadeorg.proto.enactprotocol;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.lang.PlayerMessage;

/**
 * A 'Refuse' message.
 * A 'Refuse' message is a message send from an Organization agent to a Player agent
 * containing a refusal to enact/deact a certain role.
 * @author Lukáš Kúdela
 * @since 2011-10-21
 */
public class RefuseMessage extends PlayerMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    @Override
    protected int getPerformative() {
        return ACLMessage.REFUSE;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected MessageParser createParser() {
        return new RefuseMessageParser();
    }

    @Override
    protected MessageGenerator createGenerator() {
        return new RefuseMessageGenerator();
    }
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A 'Refuse' message parser.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version %I% %G%
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
     * @version %I% %G%
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
