package example2.organizations.auction.bidder;

import example2.protocols.dutchauctionprotocol.DutchAuctionProtocol;
import example2.protocols.englishauctionprotocol.EnglishAuctionProtocol;
import jadeorg.core.Responder;

/**
 * The Bidder role responder.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Bidder_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Bidder_Responder() {
        // Add responder.
        addResponder(EnglishAuctionProtocol.getInstance());
        addResponder(DutchAuctionProtocol.getInstance());
    }
     
    // </editor-fold>
}
