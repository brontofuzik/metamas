package jadeorg.core.player.requirement;

import jadeorg.proto.jadeextensions.State;

/**
 * A requirement.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public interface Requirement extends State {
    
    Object getArgument();
    
    void setArgument(Object argument);
    
    Object getResult();
    
    void setResult(Object result);
}
