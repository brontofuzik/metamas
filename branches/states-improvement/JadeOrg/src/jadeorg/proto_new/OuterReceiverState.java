package jadeorg.proto_new;

import jadeorg.lang.simplemessages.AgreeMessage;
import jadeorg.lang.simplemessages.FailureMessage;
import jadeorg.lang.simplemessages.RefuseMessage;
import jadeorg.lang.simplemessages.SimpleMessage;
import jadeorg.proto_new.jadeextensions.FSMBehaviourReceiverState;
import jadeorg.proto_new.jadeextensions.OneShotBehaviourState;

/**
 * A top-level receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
abstract class OuterReceiverState extends FSMBehaviourReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected OuterReceiverState(String name) {
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
    
    // ------------ PROTECTED ----------
    
    protected abstract void onEntry();
    
    protected void onManager() {
        // Do nothing.
    }
    
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
    
    protected class ManagerState extends OneShotBehaviourState {
        
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
            onManager();
        }
        
        @Override
        public int onEnd() {
            return getExitValue();
        }
        
        // </editor-fold>
    }
    
    protected abstract class InnerReceiverState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        public static final int RECEIVED = 0;
        public static final int NOT_RECEIVED = 1;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected InnerReceiverState(String name) {
            super(name);
        } 
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        protected void setExitValue(int exitValue) {
            ((MultiReceiverState)getParent()).setExitValue(exitValue);
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Receive agree' inner receiver state.
     * @author Lukáš Kúdela
     * @since 2011-12-15
     * @version %I% %G%
     */
    protected class ReceiveAgree extends InnerReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-agree";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public ReceiveAgree() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Receive the 'Agree' message.
            AgreeMessage agreeMessage = (AgreeMessage)
                receive(AgreeMessage.class);
            
            // Process the message.
            if (agreeMessage != null) {
                setExitValue(RECEIVED);
            } else {
                setExitValue(NOT_RECEIVED);
            }
        }
    
        // </editor-fold>
    }
    
    /**
     * A 'Receive failure' inner receiver state.
     * @author Lukáš Kúdela
     * @since 2011-12-15
     * @version %I% %G%
     */
    protected class ReceiveRefuse extends InnerReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-refuse";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public ReceiveRefuse() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Receive the 'Refuse' message.
            RefuseMessage refuseMessage = (RefuseMessage)
                receive(RefuseMessage.class);
            
            // Process the message.
            if (refuseMessage != null) {
                setExitValue(RECEIVED);
            } else {
                setExitValue(NOT_RECEIVED);
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Receive failure' inner receiver state.
     * @author Lukáš Kúdela
     * @since 2011-12-09
     * @version %I% %G%
     */
    protected class ReceiveFailure extends InnerReceiverState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "receive-failure";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        public ReceiveFailure() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            // Receive the 'Failure' message.
            FailureMessage failureMessage = (FailureMessage)
                receive(FailureMessage.class);
            
            // Process the message.
            if (failureMessage != null) {
                setExitValue(RECEIVED);
            } else {
                setExitValue(NOT_RECEIVED);
            }
        }

        // </editor-fold>
    }
    
    protected class BlockerState extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "blocker";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected BlockerState() {
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
