package jadeorg.proto.jadeextensions;

import jadeorg.proto.Party;

/**
 * A state.
 * @author Lukáš Kúdela
 * @since 2011-12-06
 * @version %I% %G%
 */
public interface State {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    String getName();
    
    Party getParty();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    void registerTransition(int event, State targetState);
    
    void registerTransition(int event, State targetState, String[] statesToReset);
    
    void registerDefaultTransition(State targetState);
    
    void registerDefaultTransition(State targetState, String[] statesToReset);
    
    // </editor-fold>
}
