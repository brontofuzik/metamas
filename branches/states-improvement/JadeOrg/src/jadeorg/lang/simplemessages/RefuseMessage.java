package jadeorg.lang.simplemessages;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageParser;

/**
 * A 'Refuse' (simple) message.
 * @author Lukáš Kúdela
 * @since 2011-12-14
 * @version %I% %G%
 */
public class RefuseMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RefuseMessage() {
        super(ACLMessage.REFUSE);
    }
    
    // </editor-fold>    

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageParser createParser() {
        return new RefuseMessageParser();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Refuse' message parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-12-18
     * @version %I% %G%
     */
    private static class RefuseMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public Message parse(ACLMessage aclMessage) {
            // Parse the header.
            RefuseMessage refuseMessage = new RefuseMessage();
            refuseMessage.setSender(aclMessage.getSender());
            
            // Parse the body.
            refuseMessage.setContent(aclMessage.getContent());
            return refuseMessage;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
