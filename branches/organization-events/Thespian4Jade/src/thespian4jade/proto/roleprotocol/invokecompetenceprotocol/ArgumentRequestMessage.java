package thespian4jade.proto.roleprotocol.invokecompetenceprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.MessageFactory;
import thespian4jade.lang.SimpleMessage;

/**
 * A 'Competence argument request' message.
 * @author Lukáš Kúdela
 * @since 2011-12-28
 * @version %I% %G%
 */
public class ArgumentRequestMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final String CONTENT = "competence-argument?";
    
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
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
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