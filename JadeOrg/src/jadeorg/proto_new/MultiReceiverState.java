package jadeorg.proto_new;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.ACLMessageWrapper;
import jadeorg.lang.Message;
import java.util.HashMap;
import java.util.Map;

/**
 * A multi-receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-01
 * @version %I% %G%
 */
public abstract class MultiReceiverState extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    // ----- States -----
    private EntryState entry;
    private ManagerState manager = new ManagerState();
    private Map<Integer, ReceiverState> receivers = new HashMap<Integer, ReceiverState>();
    private BlockerState blocker = new BlockerState();
    private ExitState exit;
    // ------------------
    
    private ACLMessage receivedACLMessage;
    
    private int exitValue;
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected MultiReceiverState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public ACLMessage getReceivedACLMessage() {
        return receivedACLMessage;
    }
    
    public void setReceivedACLMessage(ACLMessage receivedACLMessage) {
        this.receivedACLMessage = receivedACLMessage;
    }
    
    // ---------- PROTECTED ----------
    
    protected void setEntry(EntryState entry) {
        this.entry = entry;
    }
    
    protected void setSenderAID(AID senderAID) {
        manager.setSenderAID(senderAID);
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
    
    public Message receive(Class messageClass, AID senderAID) {
        return getParty().receive(messageClass, senderAID);
    }
    
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
    
    // ---------- PRIVATE ----------

    /**
     * Registers the states and trasitions of this multi-receiver.
     */
    private void registerStatesAndTransitions() {  
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
             
        for (Map.Entry<Integer, ReceiverState> eventReceiverPair : receivers.entrySet()) {
            // manager ---[<Performative>]---> receiver
            manager.registerTransition(eventReceiverPair.getKey(), eventReceiverPair.getValue());
        }
        // manager ---[Default]---> blocker
        manager.registerDefaultTransition(blocker);
        
        for (ReceiverState receiver : receivers.values()) {
            // receiver ---[Default]---> exit
            receiver.registerDefaultTransition(receiver);
        }
        
        // blocker ---[Default]---> manager
        blocker.registerDefaultTransition(manager, new String[] { manager.getName() });
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected abstract class ReceiverState extends OneShotBehaviourState {
        
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
    
    private class ManagerState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receiver-manager";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private AID senderAID;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected ManagerState() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        public void setSenderAID(AID senderAID) {
            this.senderAID = senderAID;
        }
        
        // </editor-fold>   
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // Receive the 'Activate reply' message.
            ACLMessageWrapper activateReply = (ACLMessageWrapper)
                receive(ACLMessageWrapper.class, senderAID);

            if (activateReply != null) {
                // The 'Activate reply' message was received.
                setReceivedACLMessage(activateReply.getWrappedACLMessage());
                setExitValue(activateReply.getWrappedACLMessage().getPerformative());
            } else {
                // The 'Activate reply' message was not received.
                setExitValue(0);
            }
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
    
    // </editor-fold>
}
