package jadeorg.proto.roleprotocol.invokepowerprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.BinaryMessage;
import java.io.Serializable;

/**
 * A 'Power result' (binary) message.
 * @author Lukáš Kúdela
 * @since 2011-12-28
 * @version %I% %G%
 */
public class PowerResultMessage extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PowerResultMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public Object getResult() {
        return result;
    }
    
    public PowerResultMessage setResult(Object result) {
        this.result = result;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected Serializable getContentObject() {
        return (Serializable)result;
    }

    @Override
    protected void setContentObject(Serializable contentObject) {
        result = contentObject;
    }
    
    // </editor-fold> 
}
