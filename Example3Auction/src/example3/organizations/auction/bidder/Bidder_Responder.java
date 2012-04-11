package example3.organizations.auction.bidder;

import example3.protocols.Protocols;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.Responder;

/**
 * The 'Bidder' role responder.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
class Bidder_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Bidder_Responder() {
        // Add protocols.
        addProtocol(ProtocolRegistry.getProtocol(Protocols.ENVELOPE_AUCTION_PROTOCOL));
        addProtocol(ProtocolRegistry.getProtocol(Protocols.VICKREY_AUCTION_PROTOCOL));
//        addProtocol(ProtocolRegistry.getProtocol(Protocols.ENGLISH_AUCTION_PROTOCOL));
//        addProtocol(ProtocolRegistry.getProtocol(Protocols.DUTCH_AUCTION_PROTOCOL));
    }
     
    // </editor-fold>
}
