package thespian4jade.core.player.responsibility;

import thespian4jade.behaviours.states.IState;
import java.io.Serializable;

/**
 * An interface of a responsibility.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public interface IResponsibility<TArgument extends Serializable,
    TResult extends Serializable> extends IState {
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the responsibility argument.
     * @param argument the responsibility argument
     */
    public void setArgument(TArgument argument);
    
    /**
     * Gets the responsibility result.
     * @return the responsibility result
     */
    public TResult getResult();
    
    // </editor-fold>
}
