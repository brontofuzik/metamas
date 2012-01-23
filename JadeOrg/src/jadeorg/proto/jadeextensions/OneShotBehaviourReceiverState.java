package jadeorg.proto.jadeextensions;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A one-shot behaviour receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-07
 * @version %I% %G%
 */
public abstract class OneShotBehaviourReceiverState extends OneShotBehaviourState
    implements ReceiverState {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Receives a message.
     * @param message the message to receive
     * @param senders the senders. More precisely, their AIDs
     * @return a flag indicating whether the message has been received
     */
    @Override
    public boolean receive(Message message, AID[] senders) {
        return getParty().receive(message, senders);
    }
    
    @Override
    public boolean receive(Message message, AID sender) {
        return receive(message, new AID[] { sender });
    }
    
    // </editor-fold>
}
