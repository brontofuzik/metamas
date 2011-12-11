package jadeorg.proto_new.toplevel;

import jade.core.behaviours.OneShotBehaviour;
import jadeorg.proto_new.FSMBehaviourSenderState;
import jadeorg.proto_new.OneShotBehaviourSenderState;
import jadeorg.proto_new.OneShotBehaviourState;
import java.util.HashMap;
import java.util.Map;

/**
 * A multi-sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-01
 * @version %I% %G%
 */
public abstract class MultiSenderState extends TopLevelSenderState {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
       
    private Map<Integer, BottomLevelSenderState> senders = new HashMap<Integer, BottomLevelSenderState>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected MultiSenderState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    protected void addSender(int event, BottomLevelSenderState sender) {
        // ----- Preconditions -----
        if (sender == null) {
            throw new IllegalArgumentException("sender");
        }
        // -------------------------
        
        senders.put(event, sender);
    }
    
    protected void buildFSM() {
        registerStatesAndTransitions();
    }
    
    protected abstract int onManager();
    
    // ---------- PRIVATE ----------
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        EntryState entry = new EntryState();
        ManagerState manager = new ManagerState();
        ExitState exit = new ExitState();
        // ------------------
        
        // Register the states.
        registerFirstState(entry);
        registerState(manager);
        for(BottomLevelSenderState sender : senders.values()) {
            registerState(sender);
        }
        registerLastState(exit);
        
        // Register the transitions.
        // entry ---[Default]---> manager
        entry.registerDefaultTransition(manager);
             
        for (Map.Entry<Integer, BottomLevelSenderState> eventSenderPair : senders.entrySet()) {
            // manager ---[<Performative>]---> sender
            manager.registerTransition(eventSenderPair.getKey(), eventSenderPair.getValue());
        }
        
        for (BottomLevelSenderState sender : senders.values()) {
            // sender ---[Default]---> exit
            sender.registerDefaultTransition(exit);
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
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
            setExitValue(onManager());
        }
        
        @Override
        public int onEnd() {
            return getExitValue();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
