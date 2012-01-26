package jadeorg.proto;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A 'Send success or FAILURE' (multi-sender) state.
 * @author Lukáš Kúdela
 * @since 2011-12-24
 * @version %I% %G%
 */
public abstract class SendSuccessOrFailure extends OuterSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected SendSuccessOrFailure() {        
        addSender(SUCCESS, new MySendSuccess());
        addSender(FAILURE, new MySendFailure());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID[] getReceivers();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected abstract Message prepareMessage();
    
    /**
     * Handles the FAILURE simple message being sent.
     * Override this method to handle the FAILURE simple message being sent.
     */
    protected void onFailure() {
        // Do nothing.
    }
    
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MySendSuccess extends InnerSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
       @Override
        protected AID[] getReceivers() {
            return SendSuccessOrFailure.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
       /**
        * Prepares the success message
        * @return the success message
        */
        @Override
        protected Message prepareMessage() {
            return SendSuccessOrFailure.this.prepareMessage();
        }
    
        // </editor-fold>
    }
    
    private class MySendFailure extends SendFailure {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return SendSuccessOrFailure.this.getReceivers();
        }
        
        // </editor-fold>    
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Prepares the FAILURE simple message
         * @return the FAILURE simple message
         */
        @Override
        protected Message prepareMessage() {
            onFailure();
            return super.prepareMessage();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold> 
}
