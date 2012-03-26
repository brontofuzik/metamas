package thespian4jade.behaviours.states;

import thespian4jade.behaviours.parties.Party;

/**
 * A state.
 * @author Lukáš Kúdela
 * @since 2011-12-06
 * @version %I% %G%
 */
public interface IState {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    String getName();
    
    Party getParty();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    void registerTransition(int event, IState targetState);
    
    void registerTransition(int event, IState targetState, String[] statesToReset);
    
    void registerDefaultTransition(IState targetState);
    
    void registerDefaultTransition(IState targetState, String[] statesToReset);
    
    // </editor-fold>
}
