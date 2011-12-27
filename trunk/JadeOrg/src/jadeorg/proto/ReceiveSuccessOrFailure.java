package jadeorg.proto;

import jade.core.AID;

/**
 * A 'Receive success or failure' (multi receiver) state.
 * @author Lukáš Kúdela
 * @since 2011-12-27
 * @version %I% %G%
 */
public abstract class ReceiveSuccessOrFailure extends OuterReceiverState {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected ReceiveSuccessOrFailure(String name, AID senderAID) {
        super(name);
        
        addReceiver(new ReceiveSuccess(senderAID));
        addReceiver(new ReceiveFailure(FAILURE, senderAID));
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected abstract int onSuccessReceiver();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class ReceiveSuccess extends InnerReceiverState {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "receive-success";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveSuccess(AID senderAID) {
            super(NAME, SUCCESS, senderAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setExitValue(onSuccessReceiver());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
