package jadeorg.proto;

import jade.core.AID;
import jadeorg.proto.MultiSenderState;

/**
 * Sends a 'Agree' or 'Refuse' message.
 * @author Lukáš Kúdela
 * @since 2010-12-20
 * @version %I% %G%
 */
public abstract class SendAgreeOrRefuse extends MultiSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int AGREE = 1;
    public static final int REFUSE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected SendAgreeOrRefuse(String name, AID receiverAID) {
        super(name);
            
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
    
    private class MySendAgree extends MultiSenderState.SendAgree {
        
        MySendAgree(AID receiverAID) {
            super(receiverAID);
        }
        
        @Override
        protected void onSent() {
            onAgree();
        }
    }
    
    private class MySendRefuse extends MultiSenderState.SendRefuse {
    
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
