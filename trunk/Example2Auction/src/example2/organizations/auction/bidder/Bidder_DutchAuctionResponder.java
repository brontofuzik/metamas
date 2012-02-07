package example2.organizations.auction.bidder;

import example2.protocols.dutchauction.DutchAuctionProtocol;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.ResponderParty;

/**
 * The 'Dutch auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Bidder_DutchAuctionResponder extends ResponderParty<Bidder_Role> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Bidder_DutchAuctionResponder(ACLMessage message) {
        super(DutchAuctionProtocol.getInstance(), message);
    }
    
    // </editor-fold>
}
