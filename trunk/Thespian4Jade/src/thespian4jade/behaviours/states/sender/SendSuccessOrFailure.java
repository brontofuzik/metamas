package thespian4jade.behaviours.states.sender;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.language.Message;
import thespian4jade.language.SimpleMessage;

/**
 * A 'Send success or FAILURE' sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-24
 * @version %I% %G%
 */
public abstract class SendSuccessOrFailure<TMessage extends Message>
    extends OuterSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the SendSuccessOrFailure class.
     */
    protected SendSuccessOrFailure() {        
        addSender(SUCCESS, new SendSuccess());
        addSender(FAILURE, new SendFailure());
        
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
     * Override this method to prepare the mesasge to be sent in case of success.
     * @return the message to be sent in case of success
     */
    protected abstract TMessage prepareMessage();
    
    /**
     * Override this method to handle the FAILURE simple message being sent.
     */
    protected String onFailure() {
        // Do nothing.
        return "";
    }
    
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Send success' (inner sender) state.
     * A state in which the success message is sent.
     */
    private class SendSuccess extends InnerSenderState<TMessage> {
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
       @Override
        protected AID[] getReceivers() {
            return SendSuccessOrFailure.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
       /**
        * Prepares the success message
        * @return the success message
        */
        @Override
        protected TMessage prepareMessage() {
            return SendSuccessOrFailure.this.prepareMessage();
        }
    
        // </editor-fold>
    }
    
    /**
     * The 'Send FAILURE' (inner sender) state.
     * A state in which the FAILURE simple message is sent.
     */
    private class SendFailure extends SendSimpleMessage {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the SendFailure class.
         */
        SendFailure() {
            super(ACLMessage.FAILURE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the receivers; more precisely, their AIDs.
         * @return the receivers; more precisely, their AIDs.
         */
        @Override
        protected AID[] getReceivers() {
            return SendSuccessOrFailure.this.getReceivers();
        }
        
        // </editor-fold>    
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Prepares the FAILURE simple message
         * @return the FAILURE simple message
         */
        @Override
        protected SimpleMessage prepareMessage() {
            SimpleMessage message = super.prepareMessage();
            message.setContent(onFailure());
            return message;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold> 
}
