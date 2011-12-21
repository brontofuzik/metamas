package jadeorg.proto_new.jadeextensions;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A FSM behaviour sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-05
 * @version %I% %G%
 */
public abstract class FSMBehaviourSenderState extends FSMBehaviourState implements SenderState {
   
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected FSMBehaviourSenderState(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public void send(Message message, AID receiverAID) {
        getParty().send(message, receiverAID);
    }
    
    // </editor-fold>
}
