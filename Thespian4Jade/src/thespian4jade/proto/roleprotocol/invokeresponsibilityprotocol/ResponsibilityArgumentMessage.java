package thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.BinaryMessage;
import thespian4jade.language.IMessageFactory;
import java.io.Serializable;

/**
 * A 'Argument inform' message.
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class ResponsibilityArgumentMessage<TArgument extends Serializable>
    extends BinaryMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The responsibility argument.
     */
    private TArgument argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ResponsibilityArgumentMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the responsibility argument.
     * @return the responsibility argument
     */
    public TArgument getArgument() {
        return argument;
    }
    
    /**
     * Sets the responsibility argument.
     * @param argument the responsibility argument
     * @return this 'Responsibility argument' message (fluent interface)
     */
    public ResponsibilityArgumentMessage setArgument(TArgument argument) {
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
        return (Serializable)argument;
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
     * A 'Responsibility argument' message factory.
     * @param <TArgument> the responsibility argument type
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory<TArgument extends Serializable>
        implements IMessageFactory<ResponsibilityArgumentMessage<TArgument>> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Responsibility argument' message.
         * @return an empty 'Responsibility argument' message
         */
        @Override
        public ResponsibilityArgumentMessage<TArgument> createMessage() {
            return new ResponsibilityArgumentMessage<TArgument>();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
