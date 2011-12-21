package jadeorg.proto;

import jadeorg.proto.jadeextensions.State;
import java.util.ArrayList;
import java.util.List;

/**
 * A multi-receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-01
 * @version %I% %G%
 */
public abstract class MultiReceiverState extends OuterReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private List<InnerReceiverState> receivers = new ArrayList<InnerReceiverState>();
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public MultiReceiverState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected void addReceiver(InnerReceiverState receiver) {
        // ----- Preconditions -----
        if (receiver == null) {
            throw new IllegalArgumentException("receiver");
        }
        // -------------------------
        
        receivers.add(receiver);
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
}
