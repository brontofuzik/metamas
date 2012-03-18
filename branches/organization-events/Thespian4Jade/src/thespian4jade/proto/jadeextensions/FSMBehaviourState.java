package thespian4jade.proto.jadeextensions;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import thespian4jade.proto.Party;

/**
 * A FSM behaviour extension.
 * @author Lukáš Kúdela
 * @since 2011-12-02
 * @version %I% %G%
 */
public abstract class FSMBehaviourState extends FSMBehaviour implements State {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static int count = 0;
    
    private static final Object countLock = new Object();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the FSMBehaviourState class.
     */
    protected FSMBehaviourState() {
        synchronized (countLock) {
            count++;
            setBehaviourName(getClass().getName() + count);
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the state.
     * @return the name of the state.
     */
    public String getName() {
        return getBehaviourName();
    }
    
    /**
     * Gets the state code.
     * @return the state code
     */
    public int getCode() {
        return getName().hashCode();
    }
    
    /**
     * Gets the parent party.
     * @return the parent party
     */
    public Party getParty() {
        return (Party)getParent();
    }
    
    // ----- PRIVATE -----
    
    private FSMBehaviour getParentFSM() {
        return (FSMBehaviour)getParent();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void registerFirstState(State state) {
        registerFirstState((Behaviour)state, state.getName());
    }
    
    public void registerState(State state) {
        registerState((Behaviour)state, state.getName());
    }
    
    public void registerLastState(State state) {
        registerLastState((Behaviour)state, state.getName());
    }
    
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
    
    // ---------- PRIVATE ----------
    

    
    // </editor-fold>
}
