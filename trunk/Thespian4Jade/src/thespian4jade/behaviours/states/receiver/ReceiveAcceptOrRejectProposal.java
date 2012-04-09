package thespian4jade.behaviours.states.receiver;

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
        addReceiver(this.new ReceiveRejectProposal());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the senders; more precisely, their AIDs.
     * @return the senders; more precisely, their AIDs
     */
    protected abstract AID[] getSenders();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Handles the received ACCEPT-PROPOSAL message.
     * @param message the recived ACCEPT-PROPOSAL message
     */
    protected void onAcceptProposal(String messageContent) {
        // Do nothing.
    }
    
    /**
     * Handles the recevied REJECT-PROPOSAL message.
     * @param message the received REJECT-PROPOSAL message
     */
    protected void onRejectProposal(String messageContent) {
        // Do nothing.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Receive ACCEPT-PROPOSAL' (inner receiver) state.
     * A state in which an ACCEPT-PROPOSAL simple message is received.
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
            onAcceptProposal(message.getContent());
        }

        // </editor-fold>    
    }
    
    /**
     * The 'Receive REJECT-PROPOSAL' (inner receiver) state.
     * A state in which the REJECT-PROPOSAL simple message is received.
     */
    private class ReceiveRejectProposal extends ReceiveSimpleMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the MyReceiveRejectProposal class.
         */
        ReceiveRejectProposal() {
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
            onRejectProposal(message.getContent());
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
