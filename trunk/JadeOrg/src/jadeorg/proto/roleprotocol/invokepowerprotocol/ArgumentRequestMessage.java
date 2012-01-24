package jadeorg.proto.roleprotocol.invokepowerprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.MessageFactory;
import jadeorg.lang.SimpleMessage;

/**
 * A 'Power argument request' message.
 * @author Lukáš Kúdela
 * @since 2011-12-28
 * @version %I% %G%
 */
public class ArgumentRequestMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final String CONTENT = "power-argument?";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ArgumentRequestMessage() {
        super(ACLMessage.REQUEST);
        setContent(CONTENT);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    public static class Factory implements MessageFactory<ArgumentRequestMessage> {

        @Override
        public ArgumentRequestMessage createMessage() {
            return new ArgumentRequestMessage();
        } 
    }  
    
    // </editor-fold>
}
