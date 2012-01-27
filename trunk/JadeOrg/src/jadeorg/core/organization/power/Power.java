package jadeorg.core.organization.power;

import jadeorg.proto.jadeextensions.State;
import java.io.Serializable;

/**
 * A power.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public interface Power<TArgument extends Serializable, TResult extends Serializable>
    extends State {
    
    void setArgument(TArgument argument);
    
    TResult getResult();
}
