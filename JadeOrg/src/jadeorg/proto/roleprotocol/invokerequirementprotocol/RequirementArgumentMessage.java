package jadeorg.proto.roleprotocol.invokerequirementprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.BinaryMessage;
import jadeorg.lang.MessageFactory;
import java.io.Serializable;

/**
 * A 'Argument inform' message.
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class RequirementArgumentMessage extends BinaryMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RequirementArgumentMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public Object getArgument() {
        return argument;
    }
    
    public RequirementArgumentMessage setArgument(Object argument) {
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
    
    public static class Factory implements MessageFactory<RequirementArgumentMessage> {

        @Override
        public RequirementArgumentMessage createMessage() {
            return new RequirementArgumentMessage();
        }
    }
    
    // </editor-fold>
}
