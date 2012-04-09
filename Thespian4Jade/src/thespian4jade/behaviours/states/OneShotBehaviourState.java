package thespian4jade.behaviours.states;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import thespian4jade.behaviours.parties.Party;

/**
 * An extension of Jade's one-shot beahviour that is also a state.
 * @author Lukáš Kúdela
 * @since 2011-12-02
 * @version %I% %G%
 */
public abstract class OneShotBehaviourState extends OneShotBehaviour implements IState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The number states already created.
     * Used to generate unique state names.
     */
    private static int count = 0;
   
    /**
     * The lock on which the access to the 'count' field is synchronized.
     */
    private static final Object countLock = new Object();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the OneShotBehaviourState class.
     */
    protected OneShotBehaviourState() {
        synchronized (countLock) {
            count++;
            setBehaviourName(getClass().getName() + count);
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the state.
     * @return the name of the state
     */
    public String getName() {
        return getBehaviourName();
    }
    
    /**
     * Gets the code of the state.
     * @return the code of the state
     */
    public int getCode() {
        return getName().hashCode();
    }
    
    /**
     * Gets the containing party.
     * @return the containing party
     */
    public Party getParty() {
        return (Party)getParent();
    }
    
    // ----- PRIVATE -----
    
    /**
     * Gets the parent FSM behaviour.
     * @return the parent FSM behaviour
     */
    private FSMBehaviour getParentFSM() {
        return (FSMBehaviour)getParent();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Registers a transition to a target state triggered by an event.
     * @param event the event triggering the transition
     * @param targetState the target state
     */
    public void registerTransition(int event, IState targetState) {
        getParentFSM().registerTransition(getName(), targetState.getName(), event);
    }
 
    /**
     * Registers a transition to a target state triggered by an event.
     * Also, during the transition, some states are reset.
     * @param event the event triggering the transition
     * @param targetState the target state
     * @param statesToReset the states to reset during the transition
     */
    public void registerTransition(int event, IState targetState, String[] statesToReset) {
        getParentFSM().registerTransition(getName(), targetState.getName(), event, statesToReset);
    }

    /**
     * Registers a default transition to a target event.
     * @param targetState the target state
     */
    public void registerDefaultTransition(IState targetState) {
        getParentFSM().registerDefaultTransition(getName(), targetState.getName());
    }

    /**
     * Registers a default transition to a target event.
     * Also, during the transition, some states are reset.
     * @param targetState the target state the target state
     * @param statesToReset the states to reset during the transition
     */
    public void registerDefaultTransition(IState targetState, String[] statesToReset) {
        getParentFSM().registerDefaultTransition(getName(), targetState.getName(), statesToReset);
    }
    
    // </editor-fold>
}
