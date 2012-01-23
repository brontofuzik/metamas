package jadeorg.proto;

import jade.core.AID;

/**
 * Sends a 'Agree' or 'Refuse' message.
 * @author Lukáš Kúdela
 * @since 2010-12-20
 * @version %I% %G%
 */
public abstract class SendAgreeOrRefuse extends OuterSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int AGREE = 1;
    public static final int REFUSE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected SendAgreeOrRefuse() {            
        addSender(AGREE, this.new MySendAgree());
        addSender(REFUSE, this.new MySendRefuse());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID[] getReceivers();
    
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
    
    private class MySendAgree extends OuterSenderState.SendAgree {
            
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return SendAgreeOrRefuse.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onSent() {
            onAgree();
        }
        
        // </editor-fold>
    }
    
    private class MySendRefuse extends OuterSenderState.SendRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return SendAgreeOrRefuse.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onSent() {
            onRefuse();
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
