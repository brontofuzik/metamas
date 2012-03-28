package thespian4jade.core.player.responsibility;

import thespian4jade.behaviours.states.IState;
import java.io.Serializable;

/**
 * A responsibility.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public interface IResponsibility<TArgument extends Serializable,
    TResult extends Serializable> extends IState {
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public void setArgument(TArgument argument);
    
    public TResult getResult();
    
    // </editor-fold>
}
