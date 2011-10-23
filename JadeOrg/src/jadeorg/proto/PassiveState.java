package jadeorg.proto;

import jade.core.behaviours.CyclicBehaviour;

/**
 * A passive protocol state.
 * @author Lukáš Kúdela (2011-10-20)
 * @version 0.1
 */
public abstract class PassiveState extends CyclicBehaviour
    implements State {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The parent protocol. */
    private Protocol myProtocol;
    
    /** The exist value. */
    private Event exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /** Gets the parent protocol.
     * @returns the parent protocol
     */
    public Protocol getProtocol() {
        return myProtocol;
    }
    
    /**
     * Sets the parent protocol.
     * @protocol the parent protocol
     */
    public void setProtocol(Protocol protocol) {
        this.myProtocol = protocol;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Registers a transition from this state.
     * @param event the event triggering the transition
     * @param targetState the target state
     */
    public void registerTransition(Event event, State state) {
        myProtocol.registerTransition(this, state, event);
    }
    
    /**
     * Registers a default transition from this state.
     * A default transition is a transition triggered by any event
     * that is not associated with a custom transition.
     * @param targetState the target state.
     */
    public void registerDefaultTransition(State state) {
        myProtocol.registerDefaultTransition(this, state);
    }
    
    @Override
    public int onEnd() {
        return exitValue.getCode();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /** Transition event. */
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
