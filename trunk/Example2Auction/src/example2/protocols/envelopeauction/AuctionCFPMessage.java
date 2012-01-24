package example2.protocols.envelopeauction;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;

/**
 *
 * @author hp
 */
public class AuctionCFPMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public AuctionCFPMessage() {
        super(ACLMessage.CFP);
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
