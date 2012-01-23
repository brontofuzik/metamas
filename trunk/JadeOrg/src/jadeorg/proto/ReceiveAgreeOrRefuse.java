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
    
    protected ReceiveAgreeOrRefuse() {
        addReceiver(this.new MyReceiveAgree());
        addReceiver(this.new MyReceiveRefuse());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID[] getSenders();
    
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
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MyReceiveAgree() {
            super(AGREE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return ReceiveAgreeOrRefuse.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onReceived() {
            onAgree();
        }

        // </editor-fold>    
    }
    
    private class MyReceiveRefuse extends OuterReceiverState.ReceiveRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MyReceiveRefuse() {
            super(REFUSE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return ReceiveAgreeOrRefuse.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onReceived() {
            onRefuse();
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
