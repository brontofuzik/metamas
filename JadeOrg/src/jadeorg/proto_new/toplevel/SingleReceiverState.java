package jadeorg.proto_new.toplevel;

import jadeorg.proto_new.State;

/**
 * A single receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class SingleReceiverState extends TopLevelReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SingleReceiverState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">   
    
    protected abstract void onReceiver();
    
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
        
        // TODO
        // Register the transitions.
//        entry.registerDefaultTransition(sender);
//        
//        sender.registerDefaultTransition(exit);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Receiver extends BottomLevelReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "sender";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        Receiver() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            onReceiver();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
