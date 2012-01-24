package jadeorg.proto;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
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
    
    /**
     * An inner sender state.
     */
    protected abstract class InnerSenderState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        protected abstract AID[] getReceivers();
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            onSent();
            
            Message message = prepareMessage();
            
            // Send the message.
            send(message, getReceivers());
        }
        
        // ----- PROTECTED -----
        
        protected /* virtual */ void onSent() {
            // Do nothing.
        }
        
        protected abstract Message prepareMessage();
        
        // </editor-fold>
    }
    
    /**
     * A 'Send agree' inner sender state.
     * @author Lukáš Kúdela
     * @since 2011-12-15
     * @version %I% %G%
     */
    protected abstract class SendAgree extends InnerSenderState {  
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected final Message prepareMessage() {
            return new SimpleMessage(ACLMessage.AGREE);
        }
        
        // ----- PROTECTED -----
        
        protected /* virtual */ void onSent() {
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
    protected abstract class SendRefuse extends InnerSenderState {
                
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected final Message prepareMessage() {
            return new SimpleMessage(ACLMessage.REFUSE);
        }
        
        // ----- PROTECTED -----
        
        protected /* virtual */ void onSent() {
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
    protected abstract class SendFailure extends InnerSenderState {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected final Message prepareMessage() {
            return new SimpleMessage(ACLMessage.FAILURE);
        }
        
        // ----- PROTCTED -----
        
        protected /* virtual */ void onSent() {
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
