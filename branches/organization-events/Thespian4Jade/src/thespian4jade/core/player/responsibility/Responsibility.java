package thespian4jade.core.player.responsibility;

import thespian4jade.proto.jadeextensions.State;
import java.io.Serializable;

/**
 * A responsibility.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public interface Responsibility<TArgument extends Serializable,
    TResult extends Serializable> extends State {
        
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public void setArgument(TArgument argument);
    
    public TResult getResult();
    
    // </editor-fold>
}
