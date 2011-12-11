package jadeorg.proto_new.toplevel;

import jadeorg.proto_new.OneShotBehaviourSenderState;
import jadeorg.proto_new.State;

/**
 * A single sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class SingleSenderState extends TopLevelSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected SingleSenderState(String name) {
        super(name);
        
        registerStatesAndTransitions();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">   
    
    protected abstract void onSender();
    
    // ---------- PRIVATE ----------
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        State entry = new EntryState();
        State sender = new Sender();
        State exit = new ExitState();
        // ------------------
        
        // Register the states.
        registerFirstState(entry);
        registerState(sender);
        registerLastState(exit);
        
        // Register the transitions.
        entry.registerDefaultTransition(sender);
        
        sender.registerDefaultTransition(exit);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Sender extends BottomLevelSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "sender";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        Sender() {
            super(NAME);
        }
        
        // </editor-fold>
        
        @Override
        public void action() {
            onSender();
        }
    }
    
    // </editor-fold>
}
