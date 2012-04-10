package example1.protocols.invokefunctionprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.IMessageFactory;
import thespian4jade.language.TextMessage;

/**
 * An 'Invoke function reply' message is a message sent by the 'Invoke function'
 * protocol responder party (an executor) to the initiator party (an invoker) and
 * contains a reply that the function has been executed and its result.
 * @author Lukáš Kúdela
 * @since 2012-01-04
 * @version %I% %G%
 */
public class InvokeFunctionReplyMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The function result.
     */
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeFunctionReplyMessage class.
     */
    public InvokeFunctionReplyMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    /**
     * Gets the function result.
     * @return the function result.
     */
    public int getResult() {
        return result;
    }

    /**
     * Sets the function result.
     * @param result the function result
     */
    public void setResult(int result) {
        this.result = result;
    }
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the ACL message
     */
    @Override
    protected String generateContent() {
        return new Integer(result).toString();
    }
    
    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    protected void parseContent(String content) { 
        result = new Integer(content).intValue();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * A 'Reply' message factory.
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory implements IMessageFactory<InvokeFunctionReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Reply' message.
         * @return an empty 'Reply' message
         */
        @Override
        public InvokeFunctionReplyMessage createMessage() {
            return new InvokeFunctionReplyMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
