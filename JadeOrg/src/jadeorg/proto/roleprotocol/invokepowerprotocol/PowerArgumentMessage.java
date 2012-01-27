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
public class PowerArgumentMessage<TArgument extends Serializable>
    extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The (serializable) power argument.
     */
    private TArgument argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the PowerArgumentMessage class.
     */
    public PowerArgumentMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the power argument.
     * @return the power argument
     */
    public TArgument getArgument() {
        return argument;
    }
    
    /**
     * Sets the power argument
     * @param argument the power argument
     * @return this 'Power argument' message (fluent interface)
     */
    public PowerArgumentMessage setArgument(TArgument argument) {
        this.argument = argument;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Gets the (serializable) content object.
     * @return the (serializable) content object
     */
    @Override
    protected Serializable getContentObject() {
        return argument;
    }

    /**
     * Sets the (serializable) content object
     * @param contentObject the (serializable) content object
     */
    @Override
    protected void setContentObject(Serializable contentObject) {
        argument = (TArgument)contentObject;
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
