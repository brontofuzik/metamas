package jadeorg.proto.roleprotocol.invokerequirementprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.BinaryMessage;
import jadeorg.lang.MessageFactory;
import java.io.Serializable;

/**
 * A 'Result inform' message.
 * @author Lukáš Kúdela
 * @since 2011-12-22
 * @version %I% %G%
 */
public class RequirementResultMessage<TResult extends Serializable>
    extends BinaryMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The (serializable) requirement result.
     */
    private TResult result;
    
    // </editor-fold>`
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the RequirementResultMessage class.
     */
    public RequirementResultMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the requirement result.
     * @return the requirement result
     */
    public TResult getResult() {
        return result;
    }
    
    /**
     * Set the requirement result.
     * @param result the requirement result
     * @return this 'Requirement result' message (fluent interface)
     */
    public RequirementResultMessage setResult(TResult result) {
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
    
    public static class Factory<TResult extends Serializable>
        implements MessageFactory<RequirementResultMessage<TResult>> {

        @Override
        public RequirementResultMessage<TResult> createMessage() {
            return new RequirementResultMessage<TResult>();
        }
    }
    
    // </editor-fold>
}
