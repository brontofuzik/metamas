package jadeorg.proto;

import jade.core.AID;
import jadeorg.lang.Message;

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
    
    public SingleReceiverState() {        
        addReceiver(new SingleReceiver());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID[] getSenders();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">   
    
    protected abstract TMessage createEmptyMessage();
    
    protected /* virtual */ void handleMessage(TMessage message) {
        // Do nothing.
    }   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SingleReceiver
        extends InnerReceiverState<TMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SingleReceiver() {
            super(RECEIVED);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return SingleReceiverState.this.getSenders();
        }
        
        // </editor-fold>
        
        @Override
        protected TMessage createEmptyMessage() {
            return SingleReceiverState.this.createEmptyMessage();
        }
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(TMessage message) {
            SingleReceiverState.this.handleMessage(message);
        }
       
        // </editor-fold>
    }
    
    // </editor-fold>
}
