package jadeorg.proto_new.bottomlevel;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.ACLMessageWrapper;
import jadeorg.proto_new.OneShotBehaviourSenderState;

/**
 * A 'Send failure' (sender) state.
 * @author Lukáš Kúdela
 * @since 2011-12-09
 * @version %I% %G%
 */
public class SendFailure extends OneShotBehaviourSenderState {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "send-failure";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SendFailure() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        ACLMessageWrapper aclMessage = new ACLMessageWrapper(new ACLMessage(ACLMessage.FAILURE));
        send(ACLMessageWrapper.class, aclMessage);
    }
    
    // </editor-fold>
}
