package example2.protocols.envelopeauction;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageFactory;

/**
 * An 'Auction CFP' message.
 * An 'Auciton CFP' message is sent by an auctioneer to bidders. 
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class AuctionCFPMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public AuctionCFPMessage() {
        super(ACLMessage.CFP);
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
    
    public static class Factory implements MessageFactory<AuctionCFPMessage> {

        @Override
        public AuctionCFPMessage createMessage() {
            return new AuctionCFPMessage();
        }
    }
    
    // </editor-fold>
}
