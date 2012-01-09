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
    
    protected SendAgreeOrRefuse(AID receiverAID) {            
        addSender(AGREE, this.new MySendAgree(receiverAID));
        addSender(REFUSE, this.new MySendRefuse(receiverAID));
        
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
    
    private class MySendAgree extends OuterSenderState.SendAgree {
        
        MySendAgree(AID receiverAID) {
            super(receiverAID);
        }
        
        @Override
        protected void onSent() {
            onAgree();
        }
    }
    
    private class MySendRefuse extends OuterSenderState.SendRefuse {
    
        MySendRefuse(AID receiverAID) {
            super(receiverAID);
        }
        
        @Override
        protected void onSent() {
            onRefuse();
        }       
    }
    
    // </editor-fold>
}
