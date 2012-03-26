package example3.organizations.auction.bidder;

import example3.protocols.Protocols;
import example3.protocols.dutchauction.DutchAuctionProtocol;
import example3.protocols.englishauction.EnglishAuctionProtocol;
import example3.protocols.vickreyauction.VickreyAuctionProtocol;
import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.Responder;

/**
 * The Bidder role responder.
 * @author Luk� K�dela
 * @since 2012-01-18
 * @version %I% %G%
 */
class Bidder_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Bidder_Responder() {
        addResponder(ProtocolRegistry.getProtocol(Protocols.ENVELOPE_AUCTION_PROTOCOL), ACLMessage.CFP);
        addResponder(ProtocolRegistry.getProtocol(Protocols.VICKREY_AUCTION_PROTOCOL), ACLMessage.CFP);
        addResponder(ProtocolRegistry.getProtocol(Protocols.ENGLISH_AUCTION_PROTOCOL), ACLMessage.CFP);
        addResponder(ProtocolRegistry.getProtocol(Protocols.DUTCH_AUCTION_PROTOCOL), ACLMessage.CFP);
    }
     
    // </editor-fold>
}
