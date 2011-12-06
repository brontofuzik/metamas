package jadeorg.proto_new;

import jade.core.behaviours.OneShotBehaviour;
import java.util.HashMap;
import java.util.Map;

/**
 * A multi-sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-01
 * @version %I% %G%
 */
public abstract class MultiSenderState extends FSMBehaviourSenderState {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
       
    // ----- States -----
    private EntryState entry;
    private ManagerState manager;
    private Map<Integer, SenderState> senders = new HashMap<Integer, SenderState>();
    private ExitState exit;
    // ------------------
    
    private int exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected MultiSenderState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected void setEntry(EntryState entry) {
        this.entry = entry;
    }
    
    protected void setManager(ManagerState manager) {
        this.manager = manager;
    }
    
    protected void setExit(ExitState exit) {
        this.exit = exit;
    }
    
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
    
    protected void addSender(int event, SenderState sender) {
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
    
    // ---------- PRIVATE ----------
    
    private void registerStatesAndTransitions() {
        // Register the states.
        registerFirstState(entry);
        registerState(manager);
        for(SenderState sender : senders.values()) {
            registerState(sender);
        }
        registerLastState(exit);
        
        // Register the transitions.
        // entry ---[Default]---> manager
        entry.registerDefaultTransition(manager);
             
        for (Map.Entry<Integer, SenderState> eventSenderPair : senders.entrySet()) {
            // manager ---[<Performative>]---> sender
            manager.registerTransition(eventSenderPair.getKey(), eventSenderPair.getValue());
        }
        
        for (SenderState sender : senders.values()) {
            // sender ---[Default]---> exit
            sender.registerDefaultTransition(exit);
        }
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected abstract class ManagerState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "manager";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected ManagerState() {
            super(NAME);
        }
        
        // </editor-fold>
    }
    
    protected abstract class SenderState extends OneShotBehaviourState {
       
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected SenderState(String name) {
            super(name);
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
