package jadeorg.proto_new.jadeextensions;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A one-shot behaviour sender state.
 * @author Lukáš Kúdela
 * @since 2011-12-07
 * @version %I% %G%
 */
public abstract class OneShotBehaviourSenderState extends OneShotBehaviourState implements SenderState {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected OneShotBehaviourSenderState(String name) {
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
