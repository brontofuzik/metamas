package jadeorg.proto;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A single sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class SingleSenderState extends OuterSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    static final int SINGLE_SENDER = 0;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected SingleSenderState() {        
        addSender(SINGLE_SENDER, new SingleSender());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract AID[] getReceivers();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">   
    
    @Override
    protected int onManager() {
        return SINGLE_SENDER;
    }
    
    protected abstract Message prepareMessage();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SingleSender extends InnerSenderState {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return SingleSenderState.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected Message prepareMessage() {
            return SingleSenderState.this.prepareMessage();
        }
            
        // </editor-fold>
    }
    
    // </editor-fold>
}
