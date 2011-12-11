package jadeorg.proto_new.toplevel;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.ACLMessageWrapper;
import jadeorg.proto_new.FSMBehaviourSenderState;
import jadeorg.proto_new.OneShotBehaviourState;

/**
 * A top-level sender state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
abstract class TopLevelSenderState extends FSMBehaviourSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    TopLevelSenderState(String name) {
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
    
    protected abstract class BottomLevelSenderState extends OneShotBehaviourState {
       
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected BottomLevelSenderState(String name) {
            super(name);
        }
        
        // </editor-fold>
    }
    
    /**
     * A 'Send failure' (sender) state.
     * @author Lukáš Kúdela
     * @since 2011-12-09
     * @version %I% %G%
     */
    protected class SendFailure extends BottomLevelSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">

        private static final String NAME = "send-failure";

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Constructors">

        public SendFailure() {
            super(NAME);
        }

        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            ACLMessageWrapper aclMessage = new ACLMessageWrapper(new ACLMessage(ACLMessage.FAILURE));
            send(ACLMessageWrapper.class, aclMessage);
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
