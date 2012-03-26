package thespian4jade.protocols.role.invokeresponsibility;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.BinaryMessage;
import thespian4jade.language.IMessageFactory;
import java.io.Serializable;

/**
 * A 'Result inform' message.
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class ResponsibilityResultMessage<TResult extends Serializable>
    extends BinaryMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The (serializable) responsibility result.
     */
    private TResult result;
    
    // </editor-fold>`
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ResponsibilityResultMessage class.
     */
    public ResponsibilityResultMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the responsibility result.
     * @return the responsibility result
     */
    public TResult getResult() {
        return result;
    }
    
    /**
     * Set the responsibility result.
     * @param result the responsibility result
     * @return this 'Responsibility result' message (fluent interface)
     */
    public ResponsibilityResultMessage setResult(TResult result) {
        this.result = result;
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
        return result;
    }

    /**
     * Sets the (seralizable) content object.
     * @param contentObject the (serializable) content object
     */
    @Override
    protected void setContentObject(Serializable contentObject) {
        result = (TResult)contentObject;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A 'Responsibility result' message factory.
     * @param <TResult> the responsibility result type
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory<TResult extends Serializable>
        implements IMessageFactory<ResponsibilityResultMessage<TResult>> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Responsibility result' message.
         * @return an empty 'Responsibility result' message
         */
        @Override
        public ResponsibilityResultMessage<TResult> createMessage() {
            return new ResponsibilityResultMessage<TResult>();
        }
        
        // </editor-fold> 
    }
    
    // </editor-fold>
}
