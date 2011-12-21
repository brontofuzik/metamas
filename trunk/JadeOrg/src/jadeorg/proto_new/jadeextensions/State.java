package jadeorg.proto_new.jadeextensions;

import jadeorg.proto.Party;

/**
 * A state.
 * @author Lukáš Kúdela
 * @since 2011-12-06
 * @version %I% %G%
 */
public interface State {
    
    String getName();
    
    int getCode();
    
    Party getParty();

    void registerTransition(int event, State targetState);
    
    void registerTransition(int event, State targetState, String[] statesToReset);
    
    void registerDefaultTransition(State targetState);
    
    void registerDefaultTransition(State targetState, String[] statesToReset);
}
