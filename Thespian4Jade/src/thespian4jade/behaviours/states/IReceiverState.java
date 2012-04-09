package thespian4jade.behaviours.states;

import jade.core.AID;
import thespian4jade.language.Message;

/**
 * An interface of a state in which the party receives a message.
 * @author Lukáš Kúdela
 * @since 2011-12-06
 * @version %I% %G%
 */
public interface IReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Receives a message.
     * @param message the message to be received
     * @param senders the senders; more precisely, their AIDs
     * @return <c>true</c> if the message has been received, <c>false</c> otherwise
     */
    public boolean receive(Message message, AID... senders);
    
    // </editor-fold>
}
