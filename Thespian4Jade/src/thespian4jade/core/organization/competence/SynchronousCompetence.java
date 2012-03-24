package thespian4jade.core.organization.competence;

import thespian4jade.core.organization.Role;
import thespian4jade.behaviours.jadeextensions.FSMBehaviourState;
import java.io.Serializable;

/**
 * A FSM competence.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public abstract class SynchronousCompetence<TArgument extends Serializable, TResult extends Serializable>
    extends FSMBehaviourState implements ICompetence<TArgument, TResult> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private TArgument argument;
    
    private TResult result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
    public void setArgument(TArgument argument) {
        this.argument = argument;
    }
    
    public TResult getResult() {
        return result;
    }
    
    // ----- PROTECTED -----
    
    protected TArgument getArgument() {
        return argument;
    }
    
    protected void setResult(TResult result) {
        this.result = result;
    }
    
    // </editor-fold>
}
