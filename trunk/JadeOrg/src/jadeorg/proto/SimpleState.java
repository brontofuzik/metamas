package jadeorg.proto;

import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A simple state.
 * @author Lukáš Kúdela
 * @since 2011-12-06
 * @version %I% %G%
 */
public abstract class SimpleState extends OneShotBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected SimpleState(String name) {
        super(name);
    }
    
    // </editor-fold>
}
