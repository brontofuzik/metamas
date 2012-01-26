package jadeorg.proto;

import jadeorg.lang.MessageFactory;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.SimpleMessage;
import jadeorg.proto.jadeextensions.FSMBehaviourReceiverState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import java.util.ArrayList;
import java.util.List;

/**
 * A top-level receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class OuterReceiverState extends FSMBehaviourReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private List<InnerReceiverState> receivers = new ArrayList<InnerReceiverState>();
    
    private int exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected int getExitValue() {
        return exitValue;
    }
    
    protected void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public int onEnd() {
        return getExitValue();
    }
    
    // ------------ PROTECTED ----------
    
    protected void addReceiver(InnerReceiverState receiver) {
        // ----- Preconditions -----
        if (receiver == null) {
            throw new IllegalArgumentException("receiver");
        }
        // -------------------------
        
        receivers.add(receiver);
    }
    
    protected void buildFSM() {
        // TODO (priority: high) Remove the indirection.
        registerStatesAndTransitions();
    }
     
    protected abstract void onEntry();
    
    protected void onManager() {
        // Do nothing.
    }
    
    protected abstract void onExit();
    
    // ---------- PRIVATE ----------

    /**
     * Registers the states and trasitions of this multi-receiver.
     */
    private void registerStatesAndTransitions() {
        // ----- States -----
        State entry = new EntryState();
        State manager = new ManagerState();
        State blocker = new BlockerState();
        State exit = new ExitState();
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
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected class EntryState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            onEntry();
        }
        
        // </editor-fold>
    }
    
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
        
        private MessageFactory<TMessage> messageFactory;
        
        private int exitValue;
        
        private int outerReceiverStateExitValue;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the InnerReceiverState class.
         * @param messageFactory the message factory
         * @param outerReceiverStateExitValue the outer receiver state exit value
         */
        protected InnerReceiverState(MessageFactory<TMessage> messageFactory, int outerReceiverStateExitValue) {
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
    
    // TODO Consider replacing all Receive<PERFORMATIVE> abstract classes
    // with one abstract class.
    /**
     * A 'Receive AGREE' inner receiver state.
     * @author Lukáš Kúdela
     * @since 2011-12-15
     * @version %I% %G%
     */
    protected abstract class ReceiveAgree extends InnerReceiverState<SimpleMessage> {       
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the ReceiveAgree class.
         * @param outerReceiverStateExitValue the outer receiver state exit value
         */
        public ReceiveAgree(int outerReceiverStateExitValue) {
            super(new SimpleMessage.Factory(ACLMessage.AGREE),
                outerReceiverStateExitValue);
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Receive REFUSE' inner receiver state.
     * @author Lukáš Kúdela
     * @since 2011-12-15
     * @version %I% %G%
     */
    protected abstract class ReceiveRefuse extends InnerReceiverState<SimpleMessage> {
             
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the ReceiveRefuse class.
         * @param outerReceiverStateExitValue the outer receiver state exit value
         */
        public ReceiveRefuse(int outerReceiverStateExitValue) {
            super(new SimpleMessage.Factory(ACLMessage.REFUSE),
                outerReceiverStateExitValue);
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Receive ACCEPT_PROPOSAL' inner receiver state.
     * @author Lukáš Kúdela
     * @since 2012-01-26
     * @version %I% %G%
     */
    protected abstract class ReceiveAcceptProposal
        extends InnerReceiverState<SimpleMessage> {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public ReceiveAcceptProposal(int outerReceiverStateExitValue) {
            super(new SimpleMessage.Factory(ACLMessage.ACCEPT_PROPOSAL),
                outerReceiverStateExitValue);
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Receive REJECT PROPOSAL' inner receiver state.
     * @author Lukáš Kúdela
     * @since 2012-01-26
     * @version %I% %G%
     */
    protected abstract class ReceiveRejectProposal
        extends InnerReceiverState<SimpleMessage> {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public ReceiveRejectProposal(int outerReceiverStateExitValue) {
            super(new SimpleMessage.Factory(ACLMessage.REJECT_PROPOSAL),
                outerReceiverStateExitValue);
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Receive FAILURE' inner receiver state.
     * @author Lukáš Kúdela
     * @since 2011-12-09
     * @version %I% %G%
     */
    protected abstract class ReceiveFailure extends InnerReceiverState<SimpleMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">

        /**
         * Initializes a new instance of the ReceiveFailure class.
         * @param outerReceiverStateExitValue the outer receiver state exit value
         */
        public ReceiveFailure(int outerReceiverStateExitValue) {
            super(new SimpleMessage.Factory(ACLMessage.FAILURE),
                outerReceiverStateExitValue);
        }

        // </editor-fold>
    }
    
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
