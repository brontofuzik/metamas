package jadeorg.core.organization.power;

import jadeorg.proto.jadeextensions.State;

/**
 * A power.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public interface Power extends State {
    
    Object getArgument();
    
    void setArgument(Object argument);
    
    Object getResult();
    
    void setResult(Object result);
}
