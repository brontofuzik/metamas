package thespian4jade.behaviours.states.sender;

import jade.core.AID;
import thespian4jade.language.Message;
import thespian4jade.language.SimpleMessage;
import thespian4jade.behaviours.states.FSMBehaviourSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import java.util.HashMap;
import java.util.Map;

/**
 * An outer sender state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class OuterSenderState extends FSMBehaviourSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The inner sender states.
     */
    private Map<Integer, InnerSenderState> senders = new HashMap<Integer, InnerSenderState>();
    
    /**
     * The exit value.
     */
    private int exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the exit value.
     * @return the exit value
     */
    protected int getExitValue() {
        return exitValue;
    }
    
    /**
     * Sets the exit value.
     * @param exitValue the exit value
     */
    protected void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public int onEnd() {
        return getExitValue();
    }
    
    // ----- PROTECTED -----
    
    /**
     * Adds an inner sender state triggered by an event.
     * @param event the event triggering the inner sender state
     * @param sender the innder sender state to be added
     */
    protected void addSender(int event, InnerSenderState sender) {
        // ----- Preconditions -----
        if (sender == null) {
            throw new IllegalArgumentException("sender");
        }
        // -------------------------
        
        senders.put(event, sender);
    }
    
    /**
     * Builds the state FSM.
     */
    protected void buildFSM() {
        // ----- States -----
        EntryState entry = new EntryState();
        ManagerState manager = new ManagerState();
        ExitState exit = new ExitState();
        // ------------------
        
        // Register the states.
        registerFirstState(entry);
        registerState(manager);
        for(InnerSenderState sender : senders.values()) {
            registerState(sender);
        }
        registerLastState(exit);
        
        // Register the transitions.
        // entry ---[Default]---> manager
        entry.registerDefaultTransition(manager);           
        for (Map.Entry<Integer, InnerSenderState> eventSenderPair : senders.entrySet()) {
            // manager ---[<Performative>]---> sender
            manager.registerTransition(eventSenderPair.getKey(), eventSenderPair.getValue());
        }      
        for (InnerSenderState sender : senders.values()) {
            // sender ---[Default]---> exit
            sender.registerDefaultTransition(exit);
        }
    }
    
    /**
     * Override this method to perform some action on entering the state.
     */
    protected abstract void onEntry();
    
    /**
     * Override this method to return the outer sender state event.
     * The outer sender state event determines which inner sender state
     * will be selected.
     * @return the outer sender state event
     */
    protected abstract int onManager();
    
    /**
     * Override this method to perform some action on exiting the state.
     */
    protected abstract void onExit();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Entry' (one-shot) state.
     */
    protected class EntryState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            onEntry();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Manager' (one-shot) state.
     */
    protected class ManagerState extends OneShotBehaviourState {
             
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            setExitValue(onManager());
        }
        
        @Override
        public int onEnd() {
            return getExitValue();
        }
        
        // </editor-fold>
    }
    
    /**
     * An inner sender state.
     */
    protected abstract class InnerSenderState<TMessage extends Message>
        extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        protected abstract AID[] getReceivers();
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {            
            TMessage message = prepareMessage();
            
            // Send the message.
            send(message, getReceivers());
        }
        
        // ----- PROTECTED -----
        
        protected abstract TMessage prepareMessage();
        
        // </editor-fold>
    }
    
    /**
     * A 'Send simple message' (inner sender) state.
     * @author Lukáš Kúdela
     * @since 2012-01-26
     * @version %I% %G%
     */
    protected abstract class SendSimpleMessage
        extends InnerSenderState<SimpleMessage> {
    
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /**
         * The performative.
         */
        private int performative;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the SendSimpleMessage class.
         * @param performative the performative
         */
        protected SendSimpleMessage(int performative) {
            this.performative = performative;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Prepares the simple message.
         * @return the simple message
         */
        @Override
        protected SimpleMessage prepareMessage() {
            return new SimpleMessage(performative);
        }      
        
        // </editor-fold>
    }
    
    /**
     * The 'Exit' (one-shot) state.
     */
    protected class ExitState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            onExit();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
