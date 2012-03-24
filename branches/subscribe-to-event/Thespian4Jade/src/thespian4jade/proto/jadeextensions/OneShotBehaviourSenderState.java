package thespian4jade.proto.jadeextensions;

import jade.core.AID;
import thespian4jade.language.Message;

/**
 * A one-shot behaviour sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-07
 * @version %I% %G%
 */
public abstract class OneShotBehaviourSenderState extends OneShotBehaviourState
    implements ISenderState {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sends a message.
     * @param message the message to send
     * @param receivers the receivers. More precisely, their AIDs
     */
    @Override
    public void send(Message message, AID[] receivers) {
        getParty().send(message, receivers);
    }
    
    @Override
    public void send(Message message, AID receiver) {
        send(message, new AID[] { receiver });
    }
    
    // </editor-fold>
}
