package jadeorg.proto;

import jade.core.AID;

/**
 * A 'Send success or failure' multi sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-24
 * @version %I% %G%
 */
public abstract class SendSuccessOrFailure extends OuterSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected SendSuccessOrFailure(String name, AID receiverAID) {
        super(name);
        
        addSender(SUCCESS, new SendSuccess(receiverAID));
        addSender(FAILURE, new SendFailure(receiverAID));
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected abstract void onSuccessSender();
    
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SendSuccess extends InnerSenderState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "send-success";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SendSuccess(AID receiverAID) {
            super(NAME, receiverAID);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            onSuccessSender();
        }
    
        // </editor-fold>
    }
    
    // </editor-fold> 
}
