package example2.organizations.auction.bidder;

import example2.protocols.englishauction.EnglishAuctionProtocol;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.ResponderParty;

/**
 * The 'English auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Bidder_EnglishAuctionResponder extends ResponderParty {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Bidder_EnglishAuctionResponder(ACLMessage message) {
        super(EnglishAuctionProtocol.getInstance(), message);
    }
    
    // </editor-fold>  
}
