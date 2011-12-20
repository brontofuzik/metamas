package jadeorg.proto_new.jadeorgextensions;

import jade.core.AID;
import jadeorg.proto_new.MultiReceiverState;

/**
 * Receive an 'Agree' or 'Refuse' message.
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public abstract class ReceiveAgreeOrRefuse extends MultiReceiverState {
 
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    static final int AGREE = 1;
    static final int REFUSE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    ReceiveAgreeOrRefuse(String name, AID senderAID) {
        super(name);

        addReceiver(this.new MyReceiveAgree(senderAID));
        addReceiver(this.new MyReceiveRefuse(senderAID));
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected abstract void onAgree();
    
    protected abstract void onRefuse();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyReceiveAgree extends MultiReceiverState.ReceiveAgree {
        
        MyReceiveAgree(AID senderAID) {
            super(AGREE, senderAID);
        }
        
        @Override
        protected void onReceived() {
            onAgree();
        }
    }
    
    private class MyReceiveRefuse extends MultiReceiverState.ReceiveRefuse {
        
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
