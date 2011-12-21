package jadeorg.proto_new.jadeextensions;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A FSM behaviour receiver state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public abstract class FSMBehaviourReceiverState extends FSMBehaviourState implements ReceiverState {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected FSMBehaviourReceiverState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public boolean receive(Message message, AID senderAID) {
        return getParty().receive(message, senderAID);
    }
    
    // </editor-fold>
}
