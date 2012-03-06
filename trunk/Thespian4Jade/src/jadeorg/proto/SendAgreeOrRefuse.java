package jadeorg.proto;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.SimpleMessage;

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
        addSender(AGREE, this.new SendAgree());
        addSender(REFUSE, this.new SendRefuse());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID[] getReceivers();
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Handles the AGREE simple message being sent.
     * Override this method to handle the AGREE simple message being sent.
     */
    protected void onAgree() {
        // Do nothing.
    }
    
    /**
     * Handles the REFUSE simple message being sent.
     * Override this method to handle the REFUSE simple message being sent.
     */
    protected void onRefuse() {
        // Do nothing.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Send AGREE' (inner sender) state.
     */
    private class SendAgree extends SendSimpleMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the SendAgree class.
         */
        SendAgree() {
            super(ACLMessage.AGREE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the receivers; more precisely, their AIDs.
         * @return the receivers; more precisely, their AIDs.
         */
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
        protected SimpleMessage prepareMessage() {
            onAgree();
            return super.prepareMessage();
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send REFUSE' (inner sender) state.
     */
    private class SendRefuse extends SendSimpleMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the SendRefuse class.
         */
        SendRefuse() {
            super(ACLMessage.REFUSE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the receivers; more precisely, their AIDs.
         * @return the receivers; more precisely, their AIDs.
         */
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
        protected SimpleMessage prepareMessage() {
            onRefuse();
            return super.prepareMessage();
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
