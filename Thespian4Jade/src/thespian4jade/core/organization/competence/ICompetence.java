package thespian4jade.core.organization.competence;

import thespian4jade.behaviours.states.IState;
import java.io.Serializable;

/**
 * An interface of a competence.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public interface ICompetence
    <TArgument extends Serializable, TResult extends Serializable>
    extends IState {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the competence argument.
     * @param argument the competence argument
     */
    public void setArgument(TArgument argument);
    
    /**
     * Gets the competence result.
     * @return the competence result
     */
    public TResult getResult();
    
    // </editor-fold>
}
