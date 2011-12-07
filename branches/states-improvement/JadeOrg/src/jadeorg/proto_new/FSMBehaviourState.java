package jadeorg.proto_new;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jadeorg.proto.Party;

/**
 * A FSM behaviour extension.
 * @author Lukáš Kúdela
 * @since 2011-12-02
 * @version %I% %G%
 */
abstract class FSMBehaviourState extends FSMBehaviour implements State {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected FSMBehaviourState(String name) {
        setBehaviourName(name);
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
    
    private FSMBehaviour getParentFSM() {
        return (FSMBehaviour)getParent();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected abstract class EntryState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "entry";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected EntryState() {
            super(NAME);
        }
        
        // </editor-fold>
    }
    
    protected abstract class ExitState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "exit";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected ExitState() {
            super(NAME);
        }
        
        // </editor-fold>
    }
    
    protected abstract class SendFailure extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-failure";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected SendFailure() {
            super(NAME);
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
