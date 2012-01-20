package example2.organizations.auction.bidder;

import example2.protocols.dutchauctionprotocol.DutchAuctionProtocol;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;

/**
 * The 'Dutch auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Bidder_DutchAuctionResponder extends ResponderParty {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Bidder_DutchAuctionResponder(ACLMessage message) {
        super(message);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public Protocol getProtocol() {
        return DutchAuctionProtocol.getInstance();
    }
    
    // </editor-fold>   
}
