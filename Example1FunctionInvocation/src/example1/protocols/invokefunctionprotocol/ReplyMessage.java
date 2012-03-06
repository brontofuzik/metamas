package example1.protocols.invokefunctionprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.MessageFactory;
import jadeorg.lang.TextMessage;

/**
 * An 'Invoke function reply' message.
 * @author Luk� K�dela
 * @since 2012-01-04
 * @version %I% %G%
 */
public class ReplyMessage extends TextMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ReplyMessage() {
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
     * @author Luk� K�dela
     * @since
     * @version %I% %G%
     */
    public static class Factory implements MessageFactory<ReplyMessage> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Reply' message.
         * @return an empty 'Reply' message
         */
        @Override
        public ReplyMessage createMessage() {
            return new ReplyMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
