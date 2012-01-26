package jadeorg.proto;

import jade.core.AID;
import jadeorg.lang.SimpleMessage;

/**
 * An 'Receive ACCEPT_PROPOSAL or REJECT_PROPOSAL' (multi-receiver) state. 
 * @author Lukáš Kúdela
 * @since 2012-01-26
 * @version %I% %G%
 */
public abstract class ReceiveAcceptOrRejectProposal extends OuterReceiverState {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int ACCEPT_PROPOSAL = 0;
    public static final int REJECT_PROPOSAL = 1;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ReceiveAcceptOrRejectProposal class.
     */
    protected ReceiveAcceptOrRejectProposal() {
        addReceiver(this.new MyReceiveAcceptProposal());
        addReceiver(this.new MyReceiveRejectProposal());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the senders; more precisely, their AIDs.
     * @return the senders; more precisely, their AIDs.
     */
    protected abstract AID[] getSenders();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Handles the received ACCEPT_PROPOSAL message.
     * @param message the recived ACCEPT_PROPOSAL message
     */
    protected /* virtual */ void handleAcceptProposalMessage(SimpleMessage message) {
        // Do nothing.
    }
    
    /**
     * Handles the recevied REJECT_PROPOSAL message.
     * @param message the received REJECT_PROPOSAL message
     */
    protected /* virtual */ void handleRejectProposalMessage(SimpleMessage message) {
        // Do nothing.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * My 'Receive ACCEPT_PROPOSAL' state.
     */
    private class MyReceiveAcceptProposal extends OuterReceiverState.ReceiveAcceptProposal {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the MyReceiveAcceptProposal class.
         */
        MyReceiveAcceptProposal() {
            super(ACCEPT_PROPOSAL);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the senders; more precisely, their AIDs.
         * @return the senders; more precisely, their AIDs.
         */
        @Override
        protected AID[] getSenders() {
            return ReceiveAcceptOrRejectProposal.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the received message.
         * @param message the received message 
         */
        @Override
        protected void handleMessage(SimpleMessage message) {
            handleAcceptProposalMessage(message);
        }

        // </editor-fold>    
    }
    
    /**
     * My 'Receive REJECT_PROPOSAL' state.
     */
    private class MyReceiveRejectProposal extends OuterReceiverState.ReceiveRejectProposal {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the MyReceiveRejectProposal class.
         */
        MyReceiveRejectProposal() {
            super(REJECT_PROPOSAL);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the senders; more precisely, their AIDs.
         * @return the senders; more precisely, their AIDs.
         */
        @Override
        protected AID[] getSenders() {
            return ReceiveAcceptOrRejectProposal.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the received message
         * @param message the received message
         */
        @Override
        protected void handleMessage(SimpleMessage message) {
            handleRejectProposalMessage(message);
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
