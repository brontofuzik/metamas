package example3.organizations.auction.bidder;

import example3.protocols.dutchauction.DutchAuctionProtocol;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.ResponderParty;

/**
 * The 'Dutch auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class DutchAuction_ResponderParty extends ResponderParty<Bidder_Role> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public DutchAuction_ResponderParty(ACLMessage message) {
        super(DutchAuctionProtocol.getInstance(), message);
    }
    
    // </editor-fold>
}
