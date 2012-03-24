package thespian4jade.behaviours.receiverstate;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.language.SimpleMessage;

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
        addReceiver(this.new ReceiveAcceptProposal());
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
     * The 'Receive ACCEPT_PROPOSAL' (inner receiver) state.
     */
    private class ReceiveAcceptProposal extends ReceiveSimpleMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the MyReceiveAcceptProposal class.
         */
        ReceiveAcceptProposal() {
            super(ACLMessage.ACCEPT_PROPOSAL, ACCEPT_PROPOSAL);
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
     * The 'Receive REJECT_PROPOSAL' (inner receiver) state.
     */
    private class MyReceiveRejectProposal extends ReceiveSimpleMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the MyReceiveRejectProposal class.
         */
        MyReceiveRejectProposal() {
            super(ACLMessage.REJECT_PROPOSAL, REJECT_PROPOSAL);
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
