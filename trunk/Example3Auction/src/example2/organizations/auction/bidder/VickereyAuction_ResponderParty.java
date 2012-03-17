package example2.organizations.auction.bidder;

import example2.protocols.vickreyauction.VickreyAuctionProtocol;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.ResponderParty;

/**
 * The 'Vickerey auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class VickereyAuction_ResponderParty extends ResponderParty<Bidder_Role> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public VickereyAuction_ResponderParty(ACLMessage message) {
        super(VickreyAuctionProtocol.getInstance(), message);
    }
    
    // </editor-fold>
}
