package thespian4jade.protocols.role.invokeresponsibility;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.BinaryMessage;
import thespian4jade.language.IMessageFactory;
import java.io.Serializable;

/**
 * A 'Responsibility argument' (binary) message is sent by the 'Invoke responsibility'
 * initiator party (a role) to the responder party (a player) and carries
 * the responsibility argument.
 * @param <TArgument> the responsibility argument type
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
    
    /**
     * Initializes a new instance of the ResponsibilityArgumentMessage class.
     * The corresponding ACL message has the INFORM performative.
     */
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
     * @return this 'Responsibility argument' message (Design pattern: Fluent interface)
     */
    public ResponsibilityArgumentMessage setArgument(TArgument argument) {
        this.argument = argument;
        return this; 
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Gets the content object for the ACL message.
     * @return the content object for the ACL message
     */
    @Override
    protected Serializable getContentObject() {
        return (Serializable)argument;
    }

    /**
     * Sets the content object from the ACL message.
     * @param contentObject the content object from the ACL message
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
