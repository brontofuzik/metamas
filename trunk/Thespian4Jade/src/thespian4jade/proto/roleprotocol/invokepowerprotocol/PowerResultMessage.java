package thespian4jade.proto.roleprotocol.invokepowerprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.BinaryMessage;
import thespian4jade.lang.MessageFactory;
import java.io.Serializable;

/**
 * A 'Power result' (binary) message.
 * @param <TResult> the power result type
 * @author Lukáš Kúdela
 * @since 2011-12-28
 * @version %I% %G%
 */

public class PowerResultMessage<TResult extends Serializable>
    extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The (serializable) power result.
     */
    private TResult result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the PowerResultMessage class.
     */
    public PowerResultMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the power result.
     * @return the power result
     */
    public TResult getResult() {
        return result;
    }
    
    /**
     * Sets the power result.
     * @param result the power result
     * @return this 'Power result' message (fluent interface)
     */
    public PowerResultMessage setResult(TResult result) {
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
        return (Serializable)result;
    }

    /**
     * Sets the (serializable) content object
     * @param contentObject the (serializable) content object
     */
    @Override
    protected void setContentObject(Serializable contentObject) {
        result = (TResult)contentObject;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * A 'Power result' message factory.
     * @param <TResult> the power result type
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory<TResult extends Serializable>
        implements MessageFactory<PowerResultMessage<TResult>> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Power result' message.
         * @return an empty 'Power result' message
         */
        @Override
        public PowerResultMessage<TResult> createMessage() {
            return new PowerResultMessage<TResult>();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
