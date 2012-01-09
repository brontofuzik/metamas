package jadeorg.proto;

import jade.core.AID;

/**
 * Receive an 'Agree' or 'Refuse' message.
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public abstract class ReceiveAgreeOrRefuse extends OuterReceiverState {
 
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int AGREE = 1;
    public static final int REFUSE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected ReceiveAgreeOrRefuse(AID senderAID) {
        addReceiver(this.new MyReceiveAgree(senderAID));
        addReceiver(this.new MyReceiveRefuse(senderAID));
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected void onAgree() {
        // Do nothing.
    }
    
    protected void onRefuse() {
        // Do nothing.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyReceiveAgree extends OuterReceiverState.ReceiveAgree {
        
        MyReceiveAgree(AID senderAID) {
            super(AGREE, senderAID);
        }
        
        @Override
        protected void onReceived() {
            onAgree();
        }
    }
    
    private class MyReceiveRefuse extends OuterReceiverState.ReceiveRefuse {
        
        MyReceiveRefuse(AID senderAID) {
            super(REFUSE, senderAID);
        }
        
        @Override
        protected void onReceived() {
            onRefuse();
        }       
    }
    
    // </editor-fold>
}
