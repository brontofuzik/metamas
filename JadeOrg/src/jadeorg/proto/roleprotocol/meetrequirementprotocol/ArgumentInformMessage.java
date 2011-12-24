package jadeorg.proto.roleprotocol.meetrequirementprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.ObjectMessage;
import java.io.Serializable;

/**
 * A 'Argument inform' message.
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class ArgumentInformMessage extends ObjectMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ArgumentInformMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public Object getArgument() {
        return argument;
    }
    
    public ArgumentInformMessage setArgument(Object argument) {
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
}
