package thespian4jade.core.organization.competence;

import thespian4jade.proto.jadeextensions.State;
import java.io.Serializable;

/**
 * A competence.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public interface Competence<TArgument extends Serializable, TResult extends Serializable>
    extends State {
    
    void setArgument(TArgument argument);
    
    TResult getResult();
}
