package example2.organizations.auction.bidder;

import example2.protocols.englishauctionprotocol.EnglishAuctionProtocol;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;

/**
 * The 'English auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Bidder_EnglishAuctionResponder extends ResponderParty {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Bidder_EnglishAuctionResponder(ACLMessage message) {
        super(message);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public Protocol getProtocol() {
        return EnglishAuctionProtocol.getInstance();
    }
    
    // </editor-fold>   
}
