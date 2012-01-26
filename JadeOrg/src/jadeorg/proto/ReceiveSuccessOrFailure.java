package jadeorg.proto;

import jade.core.AID;
import jadeorg.lang.Message;
import jadeorg.lang.MessageFactory;

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
    
    protected ReceiveSuccessOrFailure(MessageFactory<TMessage> messageFactory) {
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
        
        MyReceiveSuccess(MessageFactory<TMessage> messageFactory) {
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
    
    private class MyReceiveFailure extends ReceiveFailure {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        MyReceiveFailure() {
            super(FAILURE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return ReceiveSuccessOrFailure.this.getSenders();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
