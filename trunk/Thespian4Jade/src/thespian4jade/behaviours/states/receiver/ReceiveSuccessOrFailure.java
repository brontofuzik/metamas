package thespian4jade.behaviours.states.receiver;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.language.Message;
import thespian4jade.language.IMessageFactory;
import thespian4jade.language.SimpleMessage;

/**
 * A 'Receive success or FAILURE' (multi-receiver) state.
 * @author Lukáš Kúdela
 * @since 2011-12-27
 * @version %I% %G%
 */
public abstract class ReceiveSuccessOrFailure<TMessage extends Message>
    extends OuterReceiverState {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ReceiveSuccessOrFailure class.
     * @param messageFactory 
     */
    protected ReceiveSuccessOrFailure(IMessageFactory<TMessage> messageFactory) {
        addReceiver(new ReceiveSuccess(messageFactory));
        addReceiver(new ReceiveFailure());
        
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
     * Override this method to handle the message received in case of success.
     * @return the message received in case of success
     */
    protected void handleSuccessMessage(TMessage message) {
        // Do nothing.
    }
    
    /**
     * Override this method to handle the FAILURE simple message received.
     */
    protected void onFailure(String messageContent) {
        // Do nothing.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Receive success' (inner receiver) state.
     * A state in which the success message is received.
     */
    private class ReceiveSuccess extends InnerReceiverState<TMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveSuccess(IMessageFactory<TMessage> messageFactory) {
            super(messageFactory, SUCCESS);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return ReceiveSuccessOrFailure.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void handleMessage(TMessage message) {
            handleSuccessMessage(message);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive FAILURE' (inner receiver) state.
     * A state in which the FAILURE simple message is received.
     */
    private class ReceiveFailure extends ReceiveSimpleMessage {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the ReceiveFailure class.
         */
        ReceiveFailure() {
            super(ACLMessage.FAILURE, FAILURE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the senders; more precisely, their AIDs.
         * @return the senders; more precisely, their AIDs.
         */
        @Override
        protected AID[] getSenders() {
            return ReceiveSuccessOrFailure.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void handleMessage(SimpleMessage message) {
            onFailure(message.getContent());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
