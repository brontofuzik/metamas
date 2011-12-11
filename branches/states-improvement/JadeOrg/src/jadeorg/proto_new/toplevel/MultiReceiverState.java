package jadeorg.proto_new.toplevel;

import jadeorg.proto_new.OneShotBehaviourState;
import jadeorg.proto_new.State;
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
public abstract class MultiReceiverState extends TopLevelReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<Integer, BottomLevelReceiverState> receivers = new HashMap<Integer, BottomLevelReceiverState>();
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public MultiReceiverState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected void addReceiver(int event, BottomLevelReceiverState receiver) {
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
        for(BottomLevelReceiverState receiver : receivers.values()) {
            registerState(receiver);
        }
        registerState(blocker);
        registerLastState(exit);
        
        // Register the transitions.
        // entry ---[Default]---> manager
        entry.registerDefaultTransition(manager);
        
        // manager ---[Default]---> receiver_0
        List<BottomLevelReceiverState> receiverList = getReceiverList();
        manager.registerDefaultTransition(receiverList.get(0));
             
        for (int i = 0; i < receivers.size() - 1; i++) {
            // receiver_i ---[RECEIVED]---> exit
            receiverList.get(i).registerTransition(BottomLevelReceiverState.RECEIVED, exit);
            // receiver_i ---[NOT_RECEIVED]---> receiver_(i+1)
            receiverList.get(i).registerTransition(BottomLevelReceiverState.NOT_RECEIVED, receiverList.get(i + 1));
        }
        // receiver_(N-1) ---[RECEIVED]---> exit
        receiverList.get(receiverList.size() - 1).registerTransition(BottomLevelReceiverState.RECEIVED, exit);
        // receiver_(N-1) ---[NOT_RECEIVED]---> blocker
        receiverList.get(receiverList.size() - 1).registerTransition(BottomLevelReceiverState.NOT_RECEIVED, blocker);
        
        // blocker ---[Default]---> manager
        blocker.registerDefaultTransition(manager, new String[] { manager.getName() });
    }
    
    private List<BottomLevelReceiverState> getReceiverList() {
        List<BottomLevelReceiverState> receiverList = new ArrayList<BottomLevelReceiverState>();
        for (BottomLevelReceiverState receiver : receivers.values()) {
            receiverList.add(receiver);
        }
        return receiverList;
    }
    
    // </editor-fold>
}
