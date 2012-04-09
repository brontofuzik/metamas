package thespian4jade.behaviours.states;

import jade.core.AID;
import thespian4jade.language.Message;

/**
 * An extension of Jade's one-shot beahviour that is also a sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-07
 * @version %I% %G%
 */
public abstract class OneShotBehaviourSenderState extends OneShotBehaviourState
    implements ISenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sends a message.
     * @param message the message to be sent
     * @param receivers the receivers; more precisely, their AIDs
     */
    @Override
    public void send(Message message, AID... receivers) {
        getParty().send(message, receivers);
    }
    
    // </editor-fold>
}
