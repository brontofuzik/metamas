package example3.organizations.auction.auctioneer.auction;

/**
 * An auction type.
 * @author Lukáš Kúdela
 * @since 2012-01-19
 * @version %I% %G%
 */
public enum AuctionType {
    
    /**
     * The Envelope auction,
     * a. k. a. the sealed-bid first-price auction.
     */
    ENVELOPE("Envelop auction"),
    
    /**
     * The Vickrey auction,
     * a. k. a. sealed-bid second-price auction.
     */
    VICKREY("Vickrey auction"),
    
    /**
     * The English auction,
     * a. k. a. the open-bid ascending price auction.
     */
    ENGLISH("English auction"),
    
    /**
     * The Dutch auction,
     * a. k. a. the open-bid descending price auction
     */
    DUTCH("Dutch auction");
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The name of the auction type.
     */
    private String name;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the AuctionType enum.
     * @param name the name of the auction type
     */
    private AuctionType(String name) {
        this.name = name;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
   
    /**
     * Gets the name of the auction type.
     * @return the name of the auction type
     */
    public String getName() {
        return name;
    }
    
    // </editor-fold>
}
