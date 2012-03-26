package thespian4jade.core.organization.competence;

import thespian4jade.behaviours.jadeextensions.IState;
import java.io.Serializable;

/**
 * A competence.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public interface ICompetence
    <TArgument extends Serializable, TResult extends Serializable>
    extends IState {
    
    void setArgument(TArgument argument);
    
    TResult getResult();
}
