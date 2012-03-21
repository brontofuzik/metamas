package thespian4jade.proto;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.lang.Message;
import thespian4jade.lang.IMessageFactory;

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
    
    protected ReceiveSuccessOrFailure(IMessageFactory<TMessage> messageFactory) {
        addReceiver(new MyReceiveSuccess(messageFactory));
        addReceiver(new MyReceiveFailure());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID[] getSenders();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected /* virtual */ void handleSuccessMessage(TMessage message) {
        // Do nothing.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class MyReceiveSuccess extends InnerReceiverState<TMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MyReceiveSuccess(IMessageFactory<TMessage> messageFactory) {
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
     */
    private class MyReceiveFailure extends ReceiveSimpleMessage {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the ReceiveFailure class.
         */
        MyReceiveFailure() {
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
    }
    
    // </editor-fold>
}
