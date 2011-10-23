package jadeorg.proto;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;

/**
 * A communication protocol - a FSM behaviour.
 * @author Lukáš Kúdela (2011-10-20)
 * @version 0.1
 */
public abstract class Protocol extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void registerState(State state) {
        registerState((Behaviour)state, state.getName());
        state.setProtocol(this);
    }  
    
    public void registerFirstState(State state) {
        registerFirstState((Behaviour)state, state.getName());
        state.setProtocol(this);
    }
    
    public void registerLastState(State state) {
        registerLastState((Behaviour)state, state.getName());
        state.setProtocol(this);
    }
    
    public void registerTransition(State fromState, State toState, PassiveState.Event event) {
        registerTransition(fromState.getName(), toState.getName(), event.getCode());
    }
    
    public void registerDefaultTransition(State fromState, State toState) {
        registerDefaultTransition(fromState.getName(), toState.getName());
    }
    
    // </editor-fold>
}
