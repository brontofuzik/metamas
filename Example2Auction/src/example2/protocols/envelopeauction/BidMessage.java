package example2.protocols.envelopeauction;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;

/**
 *
 * @author hp
 */
public class BidMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public BidMessage() {
        super(ACLMessage.PROPOSE);
    }
    
    // </editor-fold>
    
    @Override
    public ACLMessage generateACLMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void parseACLMessage(ACLMessage aclMessage) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
