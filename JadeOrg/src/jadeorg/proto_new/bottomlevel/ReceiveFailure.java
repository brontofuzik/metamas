package jadeorg.proto_new.bottomlevel;

import jadeorg.lang.ACLMessageWrapper;
import jadeorg.proto_new.OneShotBehaviourReceiverState;

/**
 * A 'Receive failure' (receiver) state.
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
public class ReceiveFailure extends OneShotBehaviourReceiverState {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "receive-failure";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ReceiveFailure() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        receive(ACLMessageWrapper.class);
    }
    
    // </editor-fold>
}
