package thespian4jade.proto;

import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.lang.SimpleMessage;

/**
 * A 'Receive AGREE or REFUSE' (multi-receiver) state.
 * @author Lukáš Kúdela
 * @since 2011-12-20
 * @version %I% %G%
 */
public abstract class ReceiveAgreeOrRefuse extends OuterReceiverState {
 
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    // ----- Exit values -----
    public static final int AGREE = 1;
    public static final int REFUSE = 2;
    // -----------------------
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ReceiveAgreeOrRefuse class.
     */
    protected ReceiveAgreeOrRefuse() {
        addReceiver(this.new ReceiveAgree());
        addReceiver(this.new MyReceiveRefuse());
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the senders; more precisely, their AIDs.
     * @return the senders; more precisely, their AIDs.
     */
    protected abstract AID[] getSenders();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Handles the received AGREE message.
     * @param message the received AGREE message
     */
    protected void handleAgreeMessage(SimpleMessage message) {
        // Do nothing.
    }
    
    /**
     * Handles the received REFUSE message.
     * @param message the received REFUSE message
     */
    protected void handleRefuseMessage(SimpleMessage message) {
        // Do nothing.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Receive AGREE' (inner receiver) state. 
     */
    private class ReceiveAgree extends ReceiveSimpleMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the MyReceiveAgree class.
         */
        ReceiveAgree() {
            super(ACLMessage.AGREE, AGREE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the senders; more precisely, their AIDs.
         * @return the senders; more precisely, their AIDs.
         */
        @Override
        protected AID[] getSenders() {
            return ReceiveAgreeOrRefuse.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the received message.
         * @param message the received message
         */
        @Override
        protected void handleMessage(SimpleMessage message) {
            handleAgreeMessage(message);
        }

        // </editor-fold>    
    }
    
    /**
     * The 'Receive REFUSE' (inner receiver) state.
     */
    private class MyReceiveRefuse extends ReceiveSimpleMessage {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the MyReceiveRefuse class.
         */
        MyReceiveRefuse() {
            super(ACLMessage.REFUSE, REFUSE);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Handles the received message
         * @param message the received message
         */
        @Override
        protected AID[] getSenders() {
            return ReceiveAgreeOrRefuse.this.getSenders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the received message.
         * @param message the received message
         */
        @Override
        protected void handleMessage(SimpleMessage message) {
            handleRefuseMessage(message);
        }

        // </editor-fold>
    }
    
    // </editor-fold>
}
