package jadeorg.proto;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.SimpleMessage;
import jadeorg.proto.jadeextensions.FSMBehaviourSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import java.util.HashMap;
import java.util.Map;

/**
 * A top-level sender state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class OuterSenderState extends FSMBehaviourSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Map<Integer, InnerSenderState> senders = new HashMap<Integer, InnerSenderState>();
    
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
    
    // ---------- PROTECTED ----------
    
    protected void addSender(int event, InnerSenderState sender) {
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
    
    protected abstract void onEntry();
    
    protected abstract int onManager();
    
    protected abstract void onExit();
    
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
        for(InnerSenderState sender : senders.values()) {
            registerState(sender);
        }
        registerLastState(exit);
        
        // Register the transitions.
        // entry ---[Default]---> manager
        entry.registerDefaultTransition(manager);
             
        for (Map.Entry<Integer, InnerSenderState> eventSenderPair : senders.entrySet()) {
            // manager ---[<Performative>]---> sender
            manager.registerTransition(eventSenderPair.getKey(), eventSenderPair.getValue());
        }
        
        for (InnerSenderState sender : senders.values()) {
            // sender ---[Default]---> exit
            sender.registerDefaultTransition(exit);
        }
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
            setExitValue(onManager());
        }
        
        @Override
        public int onEnd() {
            return getExitValue();
        }
        
        // </editor-fold>
    }
    
    protected abstract class InnerSenderState extends OneShotBehaviourState {
       
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private AID receiverAID;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        protected AID getReceiverAID() {
            return receiverAID;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected InnerSenderState(AID receiverAID) {
            this.receiverAID = receiverAID;
        }
        
        protected InnerSenderState() {
            this(null);
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Send agree' inner sender state.
     * @author Lukáš Kúdela
     * @since 2011-12-15
     * @version %I% %G%
     */
    protected class SendAgree extends InnerSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public SendAgree(AID receiverAID) {
            super(receiverAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            onSent();
            
            // Create the 'Agree' message.
            SimpleMessage agreeMessage = new SimpleMessage(ACLMessage.AGREE);
            
            // Send the message.
            send(agreeMessage, getReceiverAID());
        }
        
        // ----- PROTECTED -----
        
        protected void onSent() {
            // Do nothing.
        }
        
        // </editor-fold>      
    }
    
    /**
     * A 'Send refuse' inner sender state.
     * @author Lukáš Kúdela
     * @since 2011-12-15
     * @version %I% %G%
     */
    protected class SendRefuse extends InnerSenderState {
            
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public SendRefuse(AID receiverAID) {
            super(receiverAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            onSent();
            
            // Create the 'Refuse' message.
            SimpleMessage refuseMessage = new SimpleMessage(ACLMessage.REFUSE);
            
            // Send the message.
            send(refuseMessage, getReceiverAID());
        }
        
        // ----- PROTECTED -----
        
        protected void onSent() {
            // Do nothing.
        }
        
        // </editor-fold>      
    }
    
    /**
     * A 'Send failure' inner sender state.
     * @author Lukáš Kúdela
     * @since 2011-12-09
     * @version %I% %G%
     */
    protected class SendFailure extends InnerSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        public SendFailure(AID receiverAID) {
            super(receiverAID);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // Create the 'Failure' message.
            SimpleMessage failureMessage = new SimpleMessage(ACLMessage.FAILURE);
            
            // Send the message.
            send(failureMessage, getReceiverAID());
        }
        
        // ----- PROTCTED -----
        
        protected void onSent() {
            // Do nothing.
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
