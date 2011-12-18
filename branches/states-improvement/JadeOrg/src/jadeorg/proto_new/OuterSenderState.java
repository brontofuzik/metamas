package jadeorg.proto_new;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.simplemessages.SimpleMessage;
import jadeorg.proto_new.jadeextensions.FSMBehaviourSenderState;
import jadeorg.proto_new.jadeextensions.OneShotBehaviourState;

/**
 * A top-level sender state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
abstract class OuterSenderState extends FSMBehaviourSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    OuterSenderState(String name) {
        super(name);
    }
    
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
    
    protected abstract void onEntry();
    
    protected abstract void onExit();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected class EntryState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "entry";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">

        protected EntryState() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            onEntry();
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
        
        protected InnerSenderState(String name, AID receiverAID) {
            super(name);
            this.receiverAID = receiverAID;
        }
        
        protected InnerSenderState(String name) {
            this(name, null);
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
    
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-agree";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public SendAgree(AID receiverAID) {
            super(NAME, receiverAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Create the 'Agree' message.
            SimpleMessage agreeMessage = new SimpleMessage(ACLMessage.AGREE);
            
            // Send the message.
            send(agreeMessage, getReceiverAID());
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
    
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-refuse";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public SendRefuse(AID receiverAID) {
            super(NAME, receiverAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Create the 'Refuse' message.
            SimpleMessage refuseMessage = new SimpleMessage(ACLMessage.REFUSE);
            
            // Send the message.
            send(refuseMessage, getReceiverAID());
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

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-failure";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        public SendFailure(AID receiverAID) {
            super(NAME, receiverAID);
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

        // </editor-fold>
    }
    
    protected class ExitState extends OneShotBehaviourState {
    
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "exit";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected ExitState() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            onExit();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
