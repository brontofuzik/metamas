package jadeorg.proto.jadeextensions;

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
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public boolean receive(Message message, AID senderAID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
}
