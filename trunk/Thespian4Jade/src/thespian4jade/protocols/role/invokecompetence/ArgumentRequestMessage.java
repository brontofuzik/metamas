package thespian4jade.protocols.role.invokecompetence;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.IMessageFactory;
import thespian4jade.language.SimpleMessage;

/**
 * An 'Argument request' (simple) message is sent by the 'Invoke competence'
 * responder party (a role) to the initiator party (a player) and contains a
 * request to provide the competence argument.
 * @author Lukáš Kúdela
 * @since 2011-12-28
 * @version %I% %G%
 */
public class ArgumentRequestMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final String CONTENT = "competence-argument?";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ArgumentRequestMessage class.
     * The corresponding ACL message has the REQUEST performative.
     */
    public ArgumentRequestMessage() {
        super(ACLMessage.REQUEST);
        setContent(CONTENT);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * An 'Argument request' message factory.
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory implements IMessageFactory<ArgumentRequestMessage> {

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
