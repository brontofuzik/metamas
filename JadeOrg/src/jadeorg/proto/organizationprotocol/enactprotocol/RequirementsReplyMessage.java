package jadeorg.proto.organizationprotocol.enactprotocol;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.organizationprotocol.OrganizationMessage;

/**
 * A 'Requirements reply' message.
 * A 'Requirements reply' message is a message send from a player to an organization
 * containing a reply whether the role enactment requirements are met.
 * @author Lukáš Kúdela
 * @since 2011-10-21
 */
public class RequirementsReplyMessage extends OrganizationMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected MessageTemplate createPerformativeTemplate() {
        return MessageTemplate.or(
            MessageTemplate.MatchPerformative(ACLMessage.AGREE),
            MessageTemplate.MatchPerformative(ACLMessage.REFUSE));
    }
    
    @Override
    protected MessageParser createParser() {
        return new RequirementsReplyMessageParser();
    }

    @Override
    protected MessageGenerator createGenerator() {
        return new RequirementsReplyMessageGenerator();
    }
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A 'Requirements reply' message parser.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version %I% %G%
     */
    class RequirementsReplyMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public Message parse(ACLMessage aclMessage) {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private void parseContent(ACLMessage aclMessage, RequirementsReplyMessage refuseMessage) {
            // TODO
        }

        // </editor-fold>    
    }
    
    /**
     * A 'Requirements reply' message generator.
     * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
     * DP: This class plays the role of 'Concrete product' in the Abstract factory design pattern.
     * @author Lukáš Kúdela (2011-10-21)
     * @version %I% %G%
     */
    class RequirementsReplyMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public ACLMessage generate(Message message) {
            RequirementsReplyMessage refuseMessage = (RequirementsReplyMessage)message;

            // Generate the message header.
            ACLMessage aclMessage = new ACLMessage(ACLMessage.REFUSE);
            aclMessage.setProtocol(EnactProtocol.getInstance().getName());
            aclMessage.addReceiver(refuseMessage.getReceiverPlayer());

            // Generate the content.
            aclMessage.setContent(generateContent(refuseMessage));

            return aclMessage;
        }

        private String generateContent(RequirementsReplyMessage refuseMessage) {
            return "refuse";
        }

        // </editor-fold>    
    }
    
    // </editor-fold>
}
