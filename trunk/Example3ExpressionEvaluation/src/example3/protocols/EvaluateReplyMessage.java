package example3.protocols;

import jade.lang.acl.ACLMessage;
import java.io.Serializable;
import thespian4jade.lang.BinaryMessage;
import thespian4jade.lang.MessageFactory;

/**
 * A 'Evaluate reply' (binary) message.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class EvaluateReplyMessage extends BinaryMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EvaluateReplyMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public int getResult() {
        return result;
    }
    
    public EvaluateReplyMessage setResult(int result) {
        this.result = result;
        return this;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected Serializable getContentObject() {
        return new Integer(result);
    }

    @Override
    protected void setContentObject(Serializable contentObject) {
        result = ((Integer)contentObject).intValue();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    public static class Factory implements MessageFactory<EvaluateReplyMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public EvaluateReplyMessage createMessage() {
            return new EvaluateReplyMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
