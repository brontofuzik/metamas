package thespian4jade.behaviours.receiverstate;

import jade.core.AID;
import thespian4jade.language.Message;
import thespian4jade.language.IMessageFactory;

/**
 * A single receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class SingleReceiverState<TMessage extends Message>
    extends OuterReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    static final int SINGLE_RECEIVER = 0;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SingleReceiverState(IMessageFactory<TMessage> messageFactory) {        
        addReceiver(new SingleReceiver(messageFactory));
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID[] getSenders();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">   
    
    protected /* virtual */ void handleMessage(TMessage message) {
        // Do nothing.
    }   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SingleReceiver
        extends InnerReceiverState<TMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SingleReceiver(IMessageFactory<TMessage> messageFactory) {
            super(messageFactory, RECEIVED);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return SingleReceiverState.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(TMessage message) {
            SingleReceiverState.this.handleMessage(message);
        }
       
        // </editor-fold>
    }
    
    // </editor-fold>
}
