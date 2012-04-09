package thespian4jade.behaviours.states.sender;

import jade.core.AID;
import thespian4jade.language.Message;

/**
 * A single sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-11
 * @version %I% %G%
 */
public abstract class SingleSenderState<TMessage extends Message>
    extends OuterSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    static final int SINGLE_SENDER = 0;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the SingleSenderState class.
     */
    protected SingleSenderState() {        
        addSender(SINGLE_SENDER, new SingleSender());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the receivers; more precisely, their AIDs.
     * @return the receivers; more precisely, their AIDs
     */
    protected abstract AID[] getReceivers();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">   
    
    /**
     * Returns the event determining the only inner sender state.
     * @return the event determining the only inner sender stat
     */
    @Override
    protected int onManager() {
        return SINGLE_SENDER;
    }
    
    /**
     * Override this method to prepare the mesasge to be sent.
     * @return the message to be sent
     */
    protected abstract TMessage prepareMessage();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Single sender' (inner sender) state.
     */
    private class SingleSender extends InnerSenderState<TMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return SingleSenderState.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected TMessage prepareMessage() {
            return SingleSenderState.this.prepareMessage();
        }
            
        // </editor-fold>
    }
    
    // </editor-fold>
}
