package jadeorg.proto;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A 'Send AGREE or REFUSE' (multi-sender) state.
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
    
    /**
     * The 'Send AGREE' (inner sender) state.
     */
    private class MySendAgree extends OuterSenderState.SendAgree {
            
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return SendAgreeOrRefuse.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Prepares the AGREE message
         * @return the AGREE message
         */
        @Override
        protected Message prepareMessage() {
            onAgree();
            return super.prepareMessage();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send REFUSE' (sender) (inner sender) state.
     */
    private class MySendRefuse extends OuterSenderState.SendRefuse {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return SendAgreeOrRefuse.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Prepares the REFUSE message
         * @return the REFUSE message
         */
        @Override
        protected Message prepareMessage() {
            onRefuse();
            return super.prepareMessage();
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
