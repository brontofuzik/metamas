package thespian4jade.protocols.role.invokecompetence;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.BinaryMessage;
import thespian4jade.language.IMessageFactory;
import java.io.Serializable;

/**
 * A 'Competence result' (binary) message.
 * @param <TResult> the competence result type
 * @author Lukáš Kúdela
 * @since 2011-12-28
 * @version %I% %G%
 */

public class CompetenceResultMessage<TResult extends Serializable>
    extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The (serializable) competence result.
     */
    private TResult result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the CompetenceResultMessage class.
     */
    public CompetenceResultMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the competence result.
     * @return the competence result
     */
    public TResult getResult() {
        return result;
    }
    
    /**
     * Sets the competence result.
     * @param result the competence result
     * @return this 'Competence result' message (fluent interface)
     */
    public CompetenceResultMessage setResult(TResult result) {
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
     * A 'Competence result' message factory.
     * @param <TResult> the competence result type
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory<TResult extends Serializable>
        implements IMessageFactory<CompetenceResultMessage<TResult>> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Competence result' message.
         * @return an empty 'Competence result' message
         */
        @Override
        public CompetenceResultMessage<TResult> createMessage() {
            return new CompetenceResultMessage<TResult>();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
