package example2.organizations.auction.auctioneer;

/**
 * An auction type.
 * @author Lukáš Kúdela
 * @since 2012-01-19
 * @version %I% %G%
 */
public enum AuctionType {
    /**
     * The English auction,
     * a. k. a. the open-bid ascending price auction.
     */
    ENGLISH,
    
    /**
     * The Dutch auction,
     * a. k. a. the open-bid descending price auction
     */
    DUTCH,
    
    /**
     * The Envelope auction,
     * a. k. a. the sealed-bid first-price auction.
     */
    ENVELOPE,
    
    /**
     * The Vickrey auction,
     * a. k. a. sealed-bid second-price auction.
     */
    VICKREY
}
