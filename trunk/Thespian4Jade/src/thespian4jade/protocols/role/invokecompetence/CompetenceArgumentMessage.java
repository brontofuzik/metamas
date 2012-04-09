package thespian4jade.protocols.role.invokecompetence;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.BinaryMessage;
import thespian4jade.language.IMessageFactory;
import java.io.Serializable;

/**
 * A 'Competence argument' (binary) message is sent by the 'Invoke competence'
 * initiator party (a player) to the responder party (a role) and carries
 * the competence argument.
 * @param <TArgument> the competence argument type
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class CompetenceArgumentMessage<TArgument extends Serializable>
    extends BinaryMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The competence argument.
     */
    private TArgument argument;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeCompetenceRequestMessage class.
     * The corresponding ACL message has the INFORM performative.
     */
    public CompetenceArgumentMessage() {
        super(ACLMessage.INFORM);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    /**
     * Gets the competence argument.
     * @return the competence argument
     */
    public TArgument getArgument() {
        return argument;
    }
    
    /**
     * Sets the competence argument
     * @param argument the competence argument
     * @return this 'Competence argument' message (Design pattern: Fluent interface)
     */
    public CompetenceArgumentMessage setArgument(TArgument argument) {
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
        return argument;
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
     * A 'Competence argument' message factory.
     * @param <TArgument> the competence argument type
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory<TArgument extends Serializable>
        implements IMessageFactory<CompetenceArgumentMessage<TArgument>> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty 'Competence argument' message.
         * @return an empty 'Competence argument' message
         */
        @Override
        public CompetenceArgumentMessage<TArgument> createMessage() {
            return new CompetenceArgumentMessage<TArgument>();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
