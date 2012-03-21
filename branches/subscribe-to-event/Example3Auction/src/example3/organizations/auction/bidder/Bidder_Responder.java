package example3.organizations.auction.bidder;

import example3.protocols.Protocols;
import example3.protocols.dutchauction.DutchAuctionProtocol;
import example3.protocols.englishauction.EnglishAuctionProtocol;
import example3.protocols.vickreyauction.VickreyAuctionProtocol;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Responder;

/**
 * The Bidder role responder.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
class Bidder_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Bidder_Responder() {
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.ENVELOPE_AUCTION_PROTOCOL), ACLMessage.CFP);
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.VICKREY_AUCTION_PROTOCOL), ACLMessage.CFP);
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.ENGLISH_AUCTION_PROTOCOL), ACLMessage.CFP);
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.DUTCH_AUCTION_PROTOCOL), ACLMessage.CFP);
    }
     
    // </editor-fold>
}
