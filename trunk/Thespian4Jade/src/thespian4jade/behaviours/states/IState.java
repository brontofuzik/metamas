package thespian4jade.behaviours.states;

import thespian4jade.behaviours.parties.Party;

/**
 * An interface of a state in a party.
 * @author Lukáš Kúdela
 * @since 2011-12-06
 * @version %I% %G%
 */
public interface IState {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the state.
     * @return the name of the state
     */
    String getName();
    
    /**
     * Gets the containing party.
     * @return the containing party
     */
    Party getParty();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Registers a transition to a target state triggered by an event.
     * @param event the event triggering the transition
     * @param targetState the target state
     */
    void registerTransition(int event, IState targetState);

    /**
     * Registers a transition to a target state triggered by an event.
     * Also, during the transition, some states are reset.
     * @param event the event triggering the transition
     * @param targetState the target state
     * @param statesToReset the states to reset during the transition
     */
    void registerTransition(int event, IState targetState, String[] statesToReset);
    
    /**
     * Registers a default transition to a target event.
     * @param targetState the target state
     */
    void registerDefaultTransition(IState targetState);
    
    /**
     * Registers a default transition to a target event.
     * Also, during the transition, some states are reset.
     * @param targetState the target state the target state
     * @param statesToReset the states to reset during the transition
     */
    void registerDefaultTransition(IState targetState, String[] statesToReset);
    
    // </editor-fold>
}
