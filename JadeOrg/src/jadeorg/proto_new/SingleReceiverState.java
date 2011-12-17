package jadeorg.proto_new;

import jadeorg.proto_new.jadeextensions.State;

/**
 * A single receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class SingleReceiverState extends OuterReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SingleReceiverState(String name) {
        super(name);
        registerStatesAndTransitions();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">   
    
    protected abstract int onReceiver();
    
    // ---------- PRIVATE ----------
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        State entry = new EntryState();
        State manager = new ManagerState();
        State receiver = new Receiver();
        State blocker = new BlockerState();
        State exit = new ExitState();
        // ------------------
        
        // Register the states.
        registerFirstState(entry);
        registerState(manager);
        registerState(receiver);
        registerState(blocker);
        registerLastState(exit);
        
        // Register the transitions.
        entry.registerDefaultTransition(manager);
        
        manager.registerDefaultTransition(receiver);
        
        receiver.registerTransition(InnerReceiverState.RECEIVED, exit);
        receiver.registerTransition(InnerReceiverState.NOT_RECEIVED, blocker);
        
        blocker.registerDefaultTransition(manager);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Receiver extends InnerReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receiver";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        Receiver() {
            super(NAME, 0);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setExitValue(onReceiver());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
