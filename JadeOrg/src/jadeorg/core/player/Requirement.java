package jadeorg.core.player;

import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A requirement (simple) state.
 * @author Lukáš Kúdela
 * @since 2011-11-11
 * @version %I% %G%
 */
abstract class Requirement extends OneShotBehaviourState {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Requirement(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    Object getArgument() {
        return argument;
    }
    
    Requirement setArgument(Object argument) {
        this.argument = argument;
        return this;
    }
    
    Object getResult() {
        return result;
    }
    
    Requirement setResult(Object result) {
        this.result = result;
        return this;
    }
    
    // </editor-fold>
}
