package example2.organizations.auction.bidder;

import example2.protocols.englishauction.EnglishAuctionProtocol;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.ResponderParty;

/**
 * The 'English auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class EnglishAuction_ResponderParty extends ResponderParty<Bidder_Role> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public EnglishAuction_ResponderParty(ACLMessage message) {
        super(EnglishAuctionProtocol.getInstance(), message);
    }
    
    // </editor-fold>  
}
