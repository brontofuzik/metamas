package thespian4jade.core.player.responsibility;

import thespian4jade.behaviours.states.OneShotBehaviourState;
import java.io.Serializable;

/**
 * A one-shot responsibility.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public abstract class AsynchronousResponsibility<TArgument extends Serializable,
    TResult extends Serializable> extends OneShotBehaviourState
    implements IResponsibility<TArgument, TResult> {
   
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
