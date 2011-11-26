package jadeorg.proto;

import jade.core.behaviours.SimpleBehaviour;

/**
 * A protocol state.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class State extends SimpleBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String requirement;
    
    private String exception;
    
    // TODO This field probably belongs to the PassiveState class.
    /** The exit value. */
    private int exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public State(String name) {
        setBehaviourName(name);
    }
    
    public State(String name, String requirement) {
        this(name);
        this.requirement = requirement;
    }

    // TAG NO-USAGES
    public State(String name, String requirement, String exception) {
        this(name, requirement);
        this.exception = exception;
    } 
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Enums">
    
   /**
     * Transition event.
     */
    public enum Event {
        SUCCESS(0),
        FAILURE(1),
        LOOP(3);
        
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
    
    public String getRequirement() {
        return requirement;
    }
    
    public String getException() {
        return exception;
    }
    
    public int getExitValue() {
        return exitValue;
    }
    
    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }
    
    public void setExitValue(Event event) {
        setExitValue(event.getCode());
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
        return exitValue;
    }
    
    // </editor-fold>
}
