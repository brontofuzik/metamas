package jadeorg.core.player.requirement;

import jadeorg.proto.jadeextensions.State;

/**
 * A requirement.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public interface Requirement<TArgument, TResult> extends State {
        
    void setArgument(TArgument argument);
    
    TResult getResult();
}
