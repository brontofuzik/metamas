package jadeorg.proto_new;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A one-shot behaviour receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-07
 * @version %I% %G%
 */
public abstract class OneShotBehaviourReceiverState extends OneShotBehaviourState implements ReceiverState {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected OneShotBehaviourReceiverState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    public Message receive(Class messageClass, AID senderAID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public Message receive(Class messageClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
