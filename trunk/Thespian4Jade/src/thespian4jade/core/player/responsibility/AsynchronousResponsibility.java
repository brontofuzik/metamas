package thespian4jade.core.player.responsibility;

import thespian4jade.behaviours.states.OneShotBehaviourState;
import java.io.Serializable;

/**
 * An asnychronous responsibility.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public abstract class AsynchronousResponsibility<TArgument extends Serializable,
    TResult extends Serializable> extends OneShotBehaviourState
    implements IResponsibility<TArgument, TResult> {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The responsibility argument.
     */
    private TArgument argument;
    
    /**
     * The responsibility result.
     */
    private TResult result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
   
    /**
     * Sets the responsibility argument.
     * @param argument the responsibility argument
     */
    public void setArgument(TArgument argument) {
        this.argument = argument;
    }
    
    /**
     * Gets the responsibility result.
     * @return the responsibility result
     */
    public TResult getResult() {
        return result;
    }
    
    // ----- PROTECTED -----
    
    /**
     * Gets the responsibility argument.
     * @return the responsibility argument
     */
    protected TArgument getArgument() {
        return argument;
    }
    
    /**
     * Sets the responsibility result.
     * @param result the responsibility result
     */
    protected void setResult(TResult result) {
        this.result = result;
    }
    
    // </editor-fold>
}
