package thespian4jade.behaviours.states;

import jade.core.AID;
import thespian4jade.language.Message;

/**
 * A receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-06
 * @version %I% %G%
 */
public interface IReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Receives a message.
     * @param message the message to receive
     * @param senders the senders. More precisely, their AIDs
     * @return a flag indicating whether the message has been received
     */
    public boolean receive(Message message, AID[] senders);
    
    public boolean receive(Message message, AID sender);
    
    // </editor-fold>
}
