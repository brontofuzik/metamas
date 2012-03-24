package example1.protocols.invokefunctionprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.IMessageFactory;
import thespian4jade.lang.TextMessage;

/**
 * An 'Invoke function reply' message.
 * @author Lukáš Kúdela
 * @since 2012-01-04
 * @version %I% %G%
 */
public class InvokeFunctionReplyMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeFunctionReplyMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected String generateContent() {
        return new Integer(result).toString();
    }

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
