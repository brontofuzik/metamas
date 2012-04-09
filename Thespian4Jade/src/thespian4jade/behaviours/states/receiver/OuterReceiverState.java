package thespian4jade.behaviours.states.receiver;

import thespian4jade.language.IMessageFactory;
import jade.core.AID;
import thespian4jade.language.Message;
import thespian4jade.language.SimpleMessage;
import thespian4jade.behaviours.states.FSMBehaviourReceiverState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import java.util.ArrayList;
import java.util.List;

/**
 * An outer receiver state.
 * A top-level receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class OuterReceiverState extends FSMBehaviourReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The inner receiver states.
     */
    private List<InnerReceiverState> receivers = new ArrayList<InnerReceiverState>();
  
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
     * Adds an inner receiver state triggered by an event.
     * @param event the event triggering the inner receiver state
     * @param sender the innder receiver state to be added
     */
    protected void addReceiver(InnerReceiverState receiver) {
        // ----- Preconditions -----
        if (receiver == null) {
            throw new IllegalArgumentException("receiver");
        }
        // -------------------------
        
        receivers.add(receiver);
    }
    
    /**
     * Builds the state FSM.
     */
    protected void buildFSM() {
        // ----- States -----
        IState entry = new EntryState();
        IState manager = new ManagerState();
        IState blocker = new BlockerState();
        IState exit = new ExitState();
        // ------------------
        
        // Register the states.
        registerFirstState(entry);
        registerState(manager);
        for(InnerReceiverState receiver : receivers) {
            registerState(receiver);
        }
        registerState(blocker);
        registerLastState(exit);
        
        // Register the transitions.
        // entry ---[Default]---> manager
        entry.registerDefaultTransition(manager);
        
        // manager ---[Default]---> receiver_0
        manager.registerDefaultTransition(receivers.get(0));
             
        for (int i = 0; i < receivers.size() - 1; i++) {
            // receiver_i ---[RECEIVED]---> exit
            receivers.get(i).registerTransition(InnerReceiverState.RECEIVED, exit);
            // receiver_i ---[NOT_RECEIVED]---> receiver_(i+1)
            receivers.get(i).registerTransition(InnerReceiverState.NOT_RECEIVED, receivers.get(i + 1));
        }
        // receiver_(N-1) ---[RECEIVED]---> exit
        receivers.get(receivers.size() - 1).registerTransition(InnerReceiverState.RECEIVED, exit);
        // receiver_(N-1) ---[NOT_RECEIVED]---> blocker
        receivers.get(receivers.size() - 1).registerTransition(InnerReceiverState.NOT_RECEIVED, blocker);
        
        // blocker ---[Default]---> manager
        blocker.registerDefaultTransition(manager/*, new String[] { manager.getName() }*/);
    }
   
    /**
     * Override this method to perform some action on entering the state.
     */
    protected abstract void onEntry();
    
    /**
     * Override this method to return the outer receiver state event.
     * The outer receiver state event determines which inner receiver state
     * will be selected.
     * @return the outer receiver state event
     */
    protected void onManager() {
        // Do nothing.
    }
   
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
            onManager();
        }
        
        // </editor-fold>
    }
    
    /**
     * An inner receiver state.
     */
    protected abstract class InnerReceiverState<TMessage extends Message>
        extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        public static final int RECEIVED = 0;
        public static final int NOT_RECEIVED = 1;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private IMessageFactory<TMessage> messageFactory;
        
        private int exitValue;
        
        private int outerReceiverStateExitValue;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the InnerReceiverState class.
         * @param messageFactory the message factory
         * @param outerReceiverStateExitValue the outer receiver state exit value
         */
        protected InnerReceiverState(IMessageFactory<TMessage> messageFactory, int outerReceiverStateExitValue) {
            this.messageFactory = messageFactory;
            this.outerReceiverStateExitValue = outerReceiverStateExitValue;
        } 
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        protected void setExitValue(int exitValue) {
            this.exitValue = exitValue;
            if (exitValue == RECEIVED) {
                getOuterReceiverStateParent().setExitValue(outerReceiverStateExitValue);
            }
        }
        
        protected abstract AID[] getSenders();
        
        // ----- PRIVATE -----
        
        private OuterReceiverState getOuterReceiverStateParent() {
            return (OuterReceiverState)getParent();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {            
            // Receive the message.
            TMessage message = messageFactory.createMessage();
            boolean messageReceived = receive(message, getSenders());
            
            // Process the message.
            if (messageReceived) {
                //System.out.println("----- MESSAGE RECEIVED -----");
                handleMessage(message);
                setExitValue(RECEIVED);
            } else {
                //System.out.println("----- MESSAGE NOT-RECEIVED -----");
                setExitValue(NOT_RECEIVED);
            }
        }
        
        @Override
        public int onEnd() {
            return exitValue;
        }
        
        // ----- PROTECTED -----
                
        protected /* virtual */ void handleMessage(TMessage message) {
        }
       
        // </editor-fold>
    }
    
    /**
     * A 'Receive simple message' (inner receiver) state.
     * @author Lukáš Kúdela
     * @since 2012-01-26
     * @version %I% %G%
     */
    protected abstract class ReceiveSimpleMessage
        extends InnerReceiverState<SimpleMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the ReceiveAgree class.
         * @param performative the performative
         * @param outerReceiverStateExitValue the outer receiver state exit value
         */
        public ReceiveSimpleMessage(int performative, int outerReceiverStateExitValue) {
            super(new SimpleMessage.Factory(performative),
                outerReceiverStateExitValue);
        }    
        
        // </editor-fold>
    }
    
    /**
     * The 'Blocker' (one-shot) state.
     */
    protected class BlockerState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            if (myAgent.getCurQueueSize() == 0) {
                getParent().block();
            }
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
