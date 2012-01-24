package example2.protocols.envelopeauction;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageFactory;

/**
 * A 'Bid' message.
 * A 'Bid' message is sent by a bidder to an auctioneer.
 * @author Lukáš Kúdela
 * @since 2012-01-24
 * @version %I% %G%
 */
public class BidMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public BidMessage() {
        super(ACLMessage.PROPOSE);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public ACLMessage generateACLMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void parseACLMessage(ACLMessage aclMessage) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    public static class Factory implements MessageFactory<BidMessage> {

        @Override
        public BidMessage createMessage() {
            return new BidMessage();
        }
    }
    
    // </editor-fold>
}
