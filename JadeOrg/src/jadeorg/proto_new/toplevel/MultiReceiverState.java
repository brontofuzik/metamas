package jadeorg.proto_new.toplevel;

import jadeorg.proto_new.FSMBehaviourReceiverState;
import jadeorg.proto_new.OneShotBehaviourState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A multi-receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-01
 * @version %I% %G%
 */
public abstract class MultiReceiverState extends FSMBehaviourReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    // ----- States -----
    private Map<Integer, ReceiverState> receivers = new HashMap<Integer, ReceiverState>();
    // ------------------
    
    //private ACLMessage receivedACLMessage;
    
    private int exitValue;
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected MultiReceiverState(String name) {
        super(name);
    }
    
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
    
    // ---------- PROTECTED ----------
    
    protected void addReceiver(int event, ReceiverState receiver) {
        // ----- Preconditions -----
        if (receiver == null) {
            throw new IllegalArgumentException("receiver");
        }
        // -------------------------
        
        receivers.put(event, receiver);
    }
    
    protected void buildFSM() {
        registerStatesAndTransitions();
    }
    
    protected abstract void onEntry();
    
    protected void onManager() {
    }
    
    protected abstract void onExit();
    
    // ---------- PRIVATE ----------

    /**
     * Registers the states and trasitions of this multi-receiver.
     */
    private void registerStatesAndTransitions() {
        // ----- States -----
        EntryState entry = new EntryState();
        ManagerState manager = new ManagerState();
        BlockerState blocker = new BlockerState();
        ExitState exit = new ExitState();
        // ------------------
        
        // Register the states.
        registerFirstState(entry);
        registerState(manager);
        for(ReceiverState receiver : receivers.values()) {
            registerState(receiver);
        }
        registerState(blocker);
        registerLastState(exit);
        
        // Register the transitions.
        // entry ---[Default]---> manager
        entry.registerDefaultTransition(manager);
        
        // manager ---[Default]---> receiver_0
        List<ReceiverState> receiverList = getReceiverList();
        manager.registerDefaultTransition(receiverList.get(0));
             
        for (int i = 0; i < receivers.size() - 1; i++) {
            // receiver_i ---[RECEIVED]---> exit
            receiverList.get(i).registerTransition(ReceiverState.RECEIVED, exit);
            // receiver_i ---[NOT_RECEIVED]---> receiver_(i+1)
            receiverList.get(i).registerTransition(ReceiverState.NOT_RECEIVED, receiverList.get(i + 1));
        }
        // receiver_(N-1) ---[RECEIVED]---> exit
        receiverList.get(receiverList.size() - 1).registerTransition(ReceiverState.RECEIVED, exit);
        // receiver_(N-1) ---[NOT_RECEIVED]---> blocker
        receiverList.get(receiverList.size() - 1).registerTransition(exitValue, blocker);
        
        // blocker ---[Default]---> manager
        blocker.registerDefaultTransition(manager, new String[] { manager.getName() });
    }
    
    private List<ReceiverState> getReceiverList() {
        List<ReceiverState> receiverList = new ArrayList<ReceiverState>();
        for (ReceiverState receiver : receivers.values()) {
            receiverList.add(receiver);
        }
        return receiverList;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected abstract class ReceiverState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        public static final int RECEIVED = 0;
        public static final int NOT_RECEIVED = 1;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected ReceiverState(String name) {
            super(name);
        } 
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        protected void setExitValue(int exitValue) {
            ((MultiReceiverState)getParent()).setExitValue(exitValue);
        }
        
        // </editor-fold>
    }
    
    // ---------- PRIVATE ----------
    
    private class EntryState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "entry";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">

        private EntryState() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            onEntry();
        }
        
        // </editor-fold>
    }
    
    private class ManagerState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "manager";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected ManagerState() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            onManager();
        }
        
        @Override
        public int onEnd() {
            return getExitValue();
        }
        
        // </editor-fold>
    }
    
    private class BlockerState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "blocker";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        private BlockerState() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getParent().block();
        }
        
        // </editor-fold>
    }
    
    private class ExitState extends OneShotBehaviourState {
    
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "exit";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        private ExitState() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            onExit();
        }
        
        // </editor-fold>
    }
       
    // </editor-fold>
}
