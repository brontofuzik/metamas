package thespian4jade.behaviours.states.special;

import java.io.Serializable;
import thespian4jade.core.player.Player_InvokeCompetence_InitiatorParty;

/**
 * A (wrapper) state in which the 'Invoke competence' protocol initiator party is
 * (synchronously) executed.
 * @author Lukáš Kúdela
 * @since 2012-03-17
 * @version %I% %G%
 */  
public abstract class InvokeCompetenceState
    <TArgument extends Serializable, TResult extends Serializable>
    extends WrapperState<Player_InvokeCompetence_InitiatorParty> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeCompetenceState class.
     * @param competenceName the name of the competence
     * @param competenceArgument the competence argument
     */
    public InvokeCompetenceState(String competenceName, TArgument competenceArgument) {
        super(new Player_InvokeCompetence_InitiatorParty(competenceName, competenceArgument));
    }
    
    /**
     * Initializes a new instance of the InvokeCompetenceState class.
     * @param competenceName the name of the competence
     */
    public InvokeCompetenceState(String competenceName) {
        super(new Player_InvokeCompetence_InitiatorParty(competenceName));
    }
    
    /**
     * Initializes a new instance of the InvokeCompetenceState class.
     */
    public InvokeCompetenceState() {
        super(new Player_InvokeCompetence_InitiatorParty());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the competence.
     * @return the name of the competence
     */
    protected String getCompetenceName() {
        return getWrappedState().getCometenceName();
    }
    
    /**
     * Gets the competence argument.
     * Override this method to provide the competence argument.
     * @return the competence argument
     */
    protected abstract TArgument getCompetenceArgument();
    
    /**
     * Sets the competence result.
     * Override this method to retrieve the competence result.
     * @param competenceResult the competence result
     */
    protected abstract void setCompetenceResult(TResult competenceResult);
    
    // </editor-fold>   
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Provides the name of the competence and its argument to the wrapped
     * 'Invoke competence' protocol initiator party.
     * @param wrappedState the wrapped 'Invoke competence' protocol initiator party
     */
    @Override
    protected final void doActionBefore(Player_InvokeCompetence_InitiatorParty wrappedState) {
        wrappedState.setCompetenceName(getCompetenceName());
        wrappedState.setCompetenceArgument(getCompetenceArgument());
    }
    
    /**
     * Retrieves the competence result from the wrapped 'Invoke competence'
     * protocol initiator party.
     * @param wrappedState the wrapped 'Invoke competence' protocol initiator party
     */
    @Override
    protected final void doActionAfter(Player_InvokeCompetence_InitiatorParty wrappedState) {
        setCompetenceResult((TResult)wrappedState.getCompetenceResult());
    }
    
    // </editor-fold>
}
