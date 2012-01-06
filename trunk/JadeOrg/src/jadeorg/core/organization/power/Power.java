package jadeorg.core.organization.power;

import jadeorg.proto.jadeextensions.State;

/**
 * A power.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public interface Power<TArgument, TResult> extends State {
    
    void setArgument(TArgument argument);
    
    TResult getResult();
}
