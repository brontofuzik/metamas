package thespian4jade.proto.jadeextensions;

import jade.core.AID;
import thespian4jade.lang.Message;

/**
 * A sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public interface ISenderState extends IState {
   
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sends a message.
     * @param message the message to send
     * @param receivers the receivers. More precisely, their AIDs
     */
    public void send(Message message, AID[] receivers);
    
    public void send(Message message, AID receiver);
    
    // </editor-fold>
}
