package jadeorg.proto_new;

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
    
    @Override
    public Message receive(Class messageClass) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
