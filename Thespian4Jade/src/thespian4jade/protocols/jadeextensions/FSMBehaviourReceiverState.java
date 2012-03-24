package thespian4jade.protocols.jadeextensions;

import jade.core.AID;
import thespian4jade.language.Message;

/**
 * A FSM behaviour receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public abstract class FSMBehaviourReceiverState extends FSMBehaviourState
    implements IReceiverState {
    
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
