package jadeorg.proto;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;

/**
 * A passive protocol state.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class PassiveState extends CyclicBehaviour
    implements State {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The parent party. */
    private Party myParty;
    
    /** The exit value. */
    private Event exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the parent party.
     * @return the parent party
     */
    public Party getParty() {
        return myParty;
    }
    
    /**
     * Sets the parent protocol.
     * @protocol the parent protocol
     */
    public void setParty(Party protocol) {
        this.myParty = protocol;
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
    public void registerTransition(Event event, State state) {
        myParty.registerTransition(this, state, event);
    }
    
    /**
     * Registers a default transition from this state.
     * A default transition is a transition triggered by any event
     * that is not associated with a custom transition.
     * @param targetState the target state.
     */
    public void registerDefaultTransition(State state) {
        myParty.registerDefaultTransition(this, state);
    }
    
    /**
     * Receives a JadeOrg message.
     * @param messageClass the message class
     * @return the received message
     */
    public Message receive(Class messageClass, AID senderAID) {
        return getParty().receive(messageClass, senderAID);
    }
    
    @Override
    public int onEnd() {
        return exitValue.getCode();
    }
    
    // ---------- PRIVATE ----------
    
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
