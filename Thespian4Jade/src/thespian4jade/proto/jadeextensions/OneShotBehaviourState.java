package thespian4jade.proto.jadeextensions;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import thespian4jade.proto.Party;

/**
 * A one-shot behaviour extension.
 * @author Lukáš Kúdela
 * @since 2011-12-02
 * @version %I% %G%
 */
public abstract class OneShotBehaviourState extends OneShotBehaviour implements State {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected OneShotBehaviourState() {
        setBehaviourName(getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return getBehaviourName();
    }
    
    public int getCode() {
        return getName().hashCode();
    }
    
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
    
    public void registerTransition(int event, State targetState) {
        getParentFSM().registerTransition(getName(), targetState.getName(), event);
    }
    
    public void registerTransition(int event, State targetState, String[] statesToReset) {
        getParentFSM().registerTransition(getName(), targetState.getName(), event, statesToReset);
    }
    
    public void registerDefaultTransition(State targetState) {
        getParentFSM().registerDefaultTransition(getName(), targetState.getName());
    }
    
    public void registerDefaultTransition(State targetState, String[] statesToReset) {
        getParentFSM().registerDefaultTransition(getName(), targetState.getName(), statesToReset);
    }
    
    // </editor-fold>
}
