package jadeorg.proto;

import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.proto.PassiveState.Event;

/**
 * An active protocol state.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class ActiveState extends OneShotBehaviour
    implements State {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The name of this active state. */
    private String name;
    
    /** The parent party. */
    private Party myParty;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ActiveState(String name) {
        // ----- Preconditions -----
        assert name != null && !name.isEmpty();
        // -------------------------
        
        this.name = name;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return name;
    }
    
    /** Gets the parent protocol.
     * @returns the parent protocol
     */
    public Party getProtocol() {
        return myParty;
    }
    
    /**
     * Gets the parent party.
     * @return the parent party
     */
    public Party getParty() {
        return myParty;
    }
 
    /**
     * Sets the parent party.
     * @protocol the parent party
     */
    public void setParty(Party party) {
        this.myParty = party;
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
     * Sends a JadeOrg message.
     * @param messageClass the message class
     * @param message the message
     */
    public void send(Class messageClass, Message message) {
        getParty().send(messageClass, message);
    }
    
    // </editor-fold>
}
