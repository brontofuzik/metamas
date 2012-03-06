package thespian4jade.proto.roleprotocol.invokepowerprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.BinaryMessage;
import thespian4jade.lang.MessageFactory;
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

    /**
     * A 'Power argument' message factory.
     * @param <TArgument> the power argument type
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory<TArgument extends Serializable>
        implements MessageFactory<PowerArgumentMessage<TArgument>> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Power argument' message.
         * @return an empty 'Power argument' message
         */
        @Override
        public PowerArgumentMessage<TArgument> createMessage() {
            return new PowerArgumentMessage<TArgument>();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
