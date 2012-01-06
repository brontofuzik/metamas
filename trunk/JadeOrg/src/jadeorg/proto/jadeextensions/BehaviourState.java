package jadeorg.proto.jadeextensions;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jadeorg.proto.Party;

/**
 * A behaviour state.
 * @author Lukáš Kúdela
 * @since 2012-01-05
 * @version %I% %G%
 */
public abstract class BehaviourState extends Behaviour implements State {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected BehaviourState(String name) {
        setBehaviourName(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public String getName() {
        return getBehaviourName();
    }
    
    @Override
    public int getCode() {
        return getName().hashCode();
    }
    
    @Override
    public Party getParty() {
        return (Party)getParent();
    }
    
    // ----- PRIVATE -----
    
    // TODO Replace with the getParty() getter.
    private FSMBehaviour getParentFSM() {
        return (FSMBehaviour)getParent();
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public void registerTransition(int event, State targetState) {
        getParentFSM().registerTransition(getName(), targetState.getName(), event);
    }

    @Override
    public void registerTransition(int event, State targetState, String[] statesToReset) {
        getParentFSM().registerTransition(getName(), targetState.getName(), event, statesToReset);
    }

    @Override
    public void registerDefaultTransition(State targetState) {
        getParentFSM().registerDefaultTransition(getName(), targetState.getName());
    }

    @Override
    public void registerDefaultTransition(State targetState, String[] statesToReset) {
        getParentFSM().registerDefaultTransition(getName(), targetState.getName(), statesToReset);
    }
    
    // </editor-fold>
}
