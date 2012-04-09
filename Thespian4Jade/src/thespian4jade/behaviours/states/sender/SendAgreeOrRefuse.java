package thespian4jade.behaviours.states.sender;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.language.SimpleMessage;

/**
 * A 'Send AGREE or REFUSE' (multi-sender) state.
 * @author Lukáš Kúdela
 * @since 2010-12-20
 * @version %I% %G%
 */
public abstract class SendAgreeOrRefuse extends OuterSenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int AGREE = 1;
    public static final int REFUSE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the SendAgreeOrRefuse class.
     */
    protected SendAgreeOrRefuse() {            
        addSender(AGREE, this.new SendAgree());
        addSender(REFUSE, this.new SendRefuse());
        
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
     * Override this method to handle the AGREE simple message being sent.
     */
    protected String onAgree() {
        // Do nothing.
        return "";
    }
    
    /**
     * Override this method to handle the REFUSE simple message being sent.
     */
    protected String onRefuse() {
        // Do nothing.
        return "";
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Send AGREE' (inner sender) state.
     * A state in which an AGREE simple message is sent.
     */
    private class SendAgree extends SendSimpleMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the SendAgree class.
         */
        SendAgree() {
            super(ACLMessage.AGREE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the receivers; more precisely, their AIDs.
         * @return the receivers; more precisely, their AIDs.
         */
        @Override
        protected AID[] getReceivers() {
            return SendAgreeOrRefuse.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Prepares the AGREE message
         * @return the AGREE message
         */
        @Override
        protected SimpleMessage prepareMessage() {
            SimpleMessage message = super.prepareMessage();
            message.setContent(onAgree());
            return message;
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send REFUSE' (inner sender) state.
     * A state in which the REFUSE simple message is sent.
     */
    private class SendRefuse extends SendSimpleMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the SendRefuse class.
         */
        SendRefuse() {
            super(ACLMessage.REFUSE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the receivers; more precisely, their AIDs.
         * @return the receivers; more precisely, their AIDs.
         */
        @Override
        protected AID[] getReceivers() {
            return SendAgreeOrRefuse.this.getReceivers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Prepares the REFUSE message
         * @return the REFUSE message
         */
        @Override
        protected SimpleMessage prepareMessage() {
            SimpleMessage message = super.prepareMessage();
            message.setContent(onRefuse());
            return message;
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
