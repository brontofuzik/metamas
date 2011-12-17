package jadeorg.lang.simplemessages;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageParser;

/**
 * A 'Failure' (simple) message.
 * @author Lukáš Kúdela
 * @since 2011-12-14
 * @version %I% %G%
 */
public class FailureMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public FailureMessage() {
        super(ACLMessage.FAILURE);
    }
    
    // </editor-fold>    

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageParser createParser() {
        return new FailureMessageParser();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Failure' message parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-12-18
     * @version %I% %G%
     */
    private static class FailureMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public Message parse(ACLMessage aclMessage) {
            // Parse the header.
            FailureMessage failureMessage = new FailureMessage();
            failureMessage.setSender(aclMessage.getSender());
            
            // Parse the body.
            failureMessage.setContent(aclMessage.getContent());
            return failureMessage;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
