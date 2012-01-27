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
public class RequirementArgumentMessage<TArgument extends Serializable>
    extends BinaryMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The requirement argument.
     */
    private TArgument argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RequirementArgumentMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the requirement argument.
     * @return the requirement argument
     */
    public TArgument getArgument() {
        return argument;
    }
    
    /**
     * Sets the requirement argument.
     * @param argument the requirement argument
     * @return this 'Requirement argument' message (fluent interface)
     */
    public RequirementArgumentMessage setArgument(TArgument argument) {
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
     * A 'Requirement argument' message factory.
     * @param <TArgument> the requirement argument type
     */
    public static class Factory<TArgument extends Serializable>
        implements MessageFactory<RequirementArgumentMessage<TArgument>> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Requirement argument' message.
         * @return an empty 'Requirement argument' message
         */
        @Override
        public RequirementArgumentMessage<TArgument> createMessage() {
            return new RequirementArgumentMessage<TArgument>();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
