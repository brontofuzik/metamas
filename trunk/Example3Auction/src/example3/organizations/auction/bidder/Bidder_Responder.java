package example3.organizations.auction.bidder;

import example3.protocols.dutchauction.DutchAuctionProtocol;
import example3.protocols.englishauction.EnglishAuctionProtocol;
import example3.protocols.envelopeauction.EnvelopeAuctionProtocol;
import example3.protocols.vickreyauction.VickreyAuctionProtocol;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.Responder;

/**
 * The Bidder role responder.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
class Bidder_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Bidder_Responder() {
        addResponder(EnvelopeAuctionProtocol.getInstance(), ACLMessage.CFP);
        addResponder(VickreyAuctionProtocol.getInstance(), ACLMessage.CFP);
        addResponder(EnglishAuctionProtocol.getInstance(), ACLMessage.CFP);
        addResponder(DutchAuctionProtocol.getInstance(), ACLMessage.CFP);
    }
     
    // </editor-fold>
}
