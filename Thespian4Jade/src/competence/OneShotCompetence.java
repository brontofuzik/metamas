package competence;

import thespian4jade.core.organization.Role;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import java.io.Serializable;

/**
 * A one-shot competence.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public abstract class OneShotCompetence<TArgument extends Serializable, TResult extends Serializable>
    extends OneShotBehaviourState implements Competence<TArgument, TResult> {
    
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
    
    public void setResult(TResult result) {
        this.result = result;
    }
    
    protected Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold> 
}
