package jadeorg.lang.simplemessages;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageParser;

/**
 * An 'Agree' (simple) message.
 * @author Lukáš Kúdela
 * @since 2011-12-14
 * @version %I% %G%
 */
public class AgreeMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public AgreeMessage() {
        super(ACLMessage.AGREE);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageParser createParser() {
        return new AgreeMessageParser();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Agree' message parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-12-18
     * @version %I% %G%
     */
    private static class AgreeMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public Message parse(ACLMessage aclMessage) {
            // Parse the header.
            AgreeMessage agreeMessage = new AgreeMessage();
            agreeMessage.setSender(aclMessage.getSender());
            
            // Parse the body.
            agreeMessage.setContent(aclMessage.getContent());
            return agreeMessage;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
