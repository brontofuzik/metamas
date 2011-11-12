package jadeorg.proto;

import jade.core.behaviours.OneShotBehaviour;

/**
 * A protocol state.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class State extends OneShotBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    // TODO This field probably belongs to the PassiveState class.
    /** The exit value. */
    private Event exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public State(String name) {
        setBehaviourName(name);
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
    
    public Party getParty() {
        return (Party)getParent();
    }
    
    public Protocol getProtocol() {
        return getParty().getProtocol();
    }
    
    public Event getExitValue() {
        return exitValue;
    }
    
    public void setExitValue(Event exitValue) {
        this.exitValue = exitValue;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Registers a transition from this state.
     * @param event the event triggering the transition
     * @param targetState the target state
     */
    public void registerTransition(Event event, State targetState) {
        getParty().registerTransition(this, targetState, event);
    }
    
    /**
     * Registers a default transition from this state.
     * A default transition is a transition triggered by any event
     * that is not associated with a custom transition.
     * @param targetState the target state.
     */
    public void registerDefaultTransition(State targetState) {
        getParty().registerDefaultTransition(this, targetState);
    }
    
    @Override
    public int onEnd() {
        return exitValue.getCode();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * Transition event.
     */
    public enum Event {
        SUCCESS(0),
        FAILURE(1);
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private int code;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        private Event(int code) {
            this.code = code;
        }
                
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        public int getCode() {
            return code;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
