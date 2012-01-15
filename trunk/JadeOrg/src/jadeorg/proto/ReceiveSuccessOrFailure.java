package jadeorg.proto;

import jade.core.AID;

/**
 * A 'Receive success or failure' (multi receiver) state.
 * @author Lukáš Kúdela
 * @since 2011-12-27
 * @version %I% %G%
 */
public abstract class ReceiveSuccessOrFailure extends OuterReceiverState {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected ReceiveSuccessOrFailure() {
        addReceiver(new MyReceiveSuccess());
        addReceiver(new MyReceiveFailure());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID getSenderAID();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected abstract int onSuccessReceiver();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyReceiveSuccess extends InnerReceiverState {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MyReceiveSuccess() {
            super(SUCCESS);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return ReceiveSuccessOrFailure.this.getSenderAID();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setExitValue(onSuccessReceiver());
        }
        
        // </editor-fold>
    }
    
    private class MyReceiveFailure extends ReceiveFailure {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MyReceiveFailure() {
            super(FAILURE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID getSenderAID() {
            return ReceiveSuccessOrFailure.this.getSenderAID();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
