package thespian4jade.core.organization.competence;

import thespian4jade.behaviours.states.OneShotBehaviourState;
import java.io.Serializable;

/**
 * An asynchronous competence.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public abstract class AsynchronousCompetence
    <TArgument extends Serializable, TResult extends Serializable>
    extends OneShotBehaviourState implements ICompetence<TArgument, TResult> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
 
    /**
     * The competence argument.
     */
    private TArgument argument;
    
    /**
     * The competecne result.
     */
    private TResult result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the competence argument.
     * @param argument the competence argument
     */
    public void setArgument(TArgument argument) {
        this.argument = argument;
    }
    
    /**
     * Gets the competence result.
     * @return the competence result
     */
    public TResult getResult() {
        return result;
    }
    
    // ----- PROTECTED -----
    
    /**
     * Gets the competence argument.
     * @return the competence argument
     */
    protected TArgument getArgument() {
        return argument;
    }
    
    /**
     * Sets the competence result.
     * @param result the competence result
     */
    public void setResult(TResult result) {
        this.result = result;
    }
    
    // </editor-fold> 
}
