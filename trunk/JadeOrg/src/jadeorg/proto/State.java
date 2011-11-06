package jadeorg.proto;

import jadeorg.proto.PassiveState.Event;

/**
 * A protocol state - a one-shot behaviour.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public interface State {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the state.
     * @return the name of the state
     */
    public abstract String getName();
    
    public Party getParty();
    
    /**
     * Sets the parent party.
     * @protocol the parent party
     */
    public void setParty(Party party);
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Registers a transition from this state.
     * @param event the event triggering the transition
     * @param targetState the target state
     */
    public void registerTransition(Event event, State targetState);
    
    /**
     * Registers a default transition from this state.
     * A default transition is a transition triggered by any event
     * that is not associated with a custom transition.
     * @param targetState the target state.
     */
    public void registerDefaultTransition(State targetState);
    
    // </editor-fold>
}
