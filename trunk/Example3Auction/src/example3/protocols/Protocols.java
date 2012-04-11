package example3.protocols;

import example3.protocols.dutchauction.DutchAuctionProtocol;
import example3.protocols.englishauction.EnglishAuctionProtocol;
import example3.protocols.envelopeauction.EnvelopeAuctionProtocol;
import example3.protocols.vickreyauction.VickreyAuctionProtocol;

/**
 * A static class containing the application-specific (domain logic) protocols
 * used in Example 3 - Auction.
 * @author Lukáš Kúdela
 * @since 2012-03-21
 * @version %I% %G%
 */
public class Protocols {
    
    public static final Class ENVELOPE_AUCTION_PROTOCOL = EnvelopeAuctionProtocol.class;
    public static final Class VICKREY_AUCTION_PROTOCOL = VickreyAuctionProtocol.class;
    public static final Class ENGLISH_AUCTION_PROTOCOL = EnglishAuctionProtocol.class;
    public static final Class DUTCH_AUCTION_PROTOCOL = DutchAuctionProtocol.class;
}
