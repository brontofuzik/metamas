package jadeorg.proto.roleprotocol.invokepowerprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.BinaryMessage;
import jadeorg.lang.MessageFactory;
import java.io.Serializable;

/**
 * A 'Power argument' (binary) message.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class PowerArgumentMessage extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PowerArgumentMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public Object getArgument() {
        return argument;
    }
    
    public PowerArgumentMessage setArgument(Object argument) {
        this.argument = argument;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected Serializable getContentObject() {
        return (Serializable)argument;
    }

    @Override
    protected void setContentObject(Serializable contentObject) {
        argument = contentObject;
    }
    
    // </editor-fold>    
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    public static class Factory implements MessageFactory<PowerArgumentMessage> {

        @Override
        public PowerArgumentMessage createMessage() {
            return new PowerArgumentMessage();
        }    
    }
    
    // </editor-fold>
}
