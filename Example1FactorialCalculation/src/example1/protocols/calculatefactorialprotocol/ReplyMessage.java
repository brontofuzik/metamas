package example1.protocols.calculatefactorialprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.TextMessage;

/**
 * A 'Calculate factorial reply' message.
 * @author Lukáš Kúdela
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
}
