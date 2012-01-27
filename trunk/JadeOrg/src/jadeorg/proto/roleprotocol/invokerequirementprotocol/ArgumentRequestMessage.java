package jadeorg.proto.roleprotocol.invokerequirementprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.MessageFactory;
import jadeorg.lang.SimpleMessage;

/**
 * 
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class ArgumentRequestMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final String CONTENT = "requirement-argument?";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ArgumentRequestMessage() {
        super(ACLMessage.REQUEST);
        setContent(CONTENT);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A 'Argument request' message factory.
     */
    public static class Factory implements MessageFactory<ArgumentRequestMessage> {

        /**
         * Creates an empty 'Argument request' message.
         * @return an empty 'Argument request' message
         */
        @Override
        public ArgumentRequestMessage createMessage() {
            return new ArgumentRequestMessage();
        }
    }
    
    // </editor-fold>
}
