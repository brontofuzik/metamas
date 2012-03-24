package thespian4jade.protocols.jadeextensions;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import thespian4jade.protocols.Party;

/**
 * A one-shot behaviour extension.
 * @author Lukáš Kúdela
 * @since 2011-12-02
 * @version %I% %G%
 */
public abstract class OneShotBehaviourState extends OneShotBehaviour implements IState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static int count = 0;
    
    private static final Object countLock = new Object();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected OneShotBehaviourState() {
        synchronized (countLock) {
            count++;
            setBehaviourName(getClass().getName() + count);
        }
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
    
    private FSMBehaviour getParentFSM() {
        return (FSMBehaviour)getParent();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void registerTransition(int event, IState targetState) {
        getParentFSM().registerTransition(getName(), targetState.getName(), event);
    }
    
    public void registerTransition(int event, IState targetState, String[] statesToReset) {
        getParentFSM().registerTransition(getName(), targetState.getName(), event, statesToReset);
    }
    
    public void registerDefaultTransition(IState targetState) {
        getParentFSM().registerDefaultTransition(getName(), targetState.getName());
    }
    
    public void registerDefaultTransition(IState targetState, String[] statesToReset) {
        getParentFSM().registerDefaultTransition(getName(), targetState.getName(), statesToReset);
    }
    
    // </editor-fold>
}
