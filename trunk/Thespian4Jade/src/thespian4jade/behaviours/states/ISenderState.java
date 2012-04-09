package thespian4jade.behaviours.states;

import jade.core.AID;
import thespian4jade.language.Message;

/**
 * An interface of a state in which the party sends a message.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public interface ISenderState extends IState {
   
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sends a message.
     * @param message the message to be sent
     * @param receivers the receivers; more precisely, their AIDs
     */
    public void send(Message message, AID... receivers);
    
    // </editor-fold>
}
