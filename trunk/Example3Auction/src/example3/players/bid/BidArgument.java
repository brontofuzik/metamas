package example3.players.bid;

import example3.organizations.auction.auctioneer.auction.AuctionType;
import java.io.Serializable;

/**
 * A 'Bid' responsibility argument.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class BidArgument implements Serializable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final long serialVersionUID = 1L;
    
    /**
     * The type of the auction.
     */
    private AuctionType auctionType;
    
    /**
     * The name of the item.
     */
    private String itemName;
    
    /**
     * The current price.
     * Only applicable to the English and Dutch auctions.
     */
    private Double currentPrice;
    
    /**
     * The bid change.
     * Only applicable to the English and Dutch auctions.
     */
    private Double bidChange;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the BidArgument class.
     * @param auctionType the auction type
     * @param itemName the name of the item
     * @param currentPrice the current price
     * @param bidChange the bid change
     */
    private BidArgument(AuctionType auctionType, String itemName,
        Double currentPrice, Double bidChange) {
        // ----- Preconditions -----
        assert auctionType != null;
        assert itemName != null && !itemName.isEmpty();
        assert currentPrice == null || currentPrice > 0;
        assert bidChange == null || bidChange > 0;
        // -------------------------
        
        this.auctionType = auctionType;
        this.itemName = itemName;
        this.currentPrice = currentPrice;
        this.bidChange = bidChange;
    }   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the type of the auction.
     * @return the type of the auction
     */
    public AuctionType getAuctionType() {
        return auctionType;
    }
    
    /**
     * Gets the name of the item.
     * @return the name of the item
     */
    public String getItemName() {
        return itemName;
    }
    
    /**
     * Gets the current price of the item.
     * @return the current price of the item
     */
    public double getCurrentPrice() {
        return currentPrice;
    }
    
    /**
     * Get the bid change.
     * @return the bid change
     */
    public double getBidChange() {
        return bidChange;
    }
  
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a bid argument for the English auction.
     * Design pattern: Static factory method
     * @param itemName the name of the item
     * @param currentPrice the current price
     * @param bidIncrement the bid increment
     * @return the bid argument for the English auction
     */
    public static BidArgument createEnglishBidArgument(String itemName,
        double currentPrice, double bidIncrement) {
        return new BidArgument(AuctionType.ENGLISH, itemName, 
            new Double(currentPrice), new Double(bidIncrement));
    }
    
    /**
     * Creates a bid argument for the Dutch auction.
     * Design pattern: Static factory method
     * @param itemName the name of the item
     * @param currentPrice the current price
     * @param bidDecrement the bid decrement
     * @return the bid argument for the Dutch auction
     */
    public static BidArgument createDutchBidArgument(String itemName,
        double currentPrice, double bidDecrement) {
        return new BidArgument(AuctionType.DUTCH, itemName, 
            new Double(currentPrice), new Double(bidDecrement));     
    }
    
    /**
     * Creates a bid argument for the Envelope auction.
     * Design pattern: Static factory method
     * @param itemName the name of the item
     * @return the bid argument for the Envelope auction
     */
    public static BidArgument createEnvelopeBidArgument(String itemName) {
        return new BidArgument(AuctionType.ENVELOPE, itemName, null, null);
    }
    
    /**
     * Creates a bid argument for the Vickrey auction.
     * Design pattern: Static factory method
     * @param itemName the name of the item
     * @return the bid argument for the Vickrey auction
     */
    public static BidArgument createVickreyBidArgument(String itemName) {
        return new BidArgument(AuctionType.VICKREY, itemName, null, null);
    }
    
    // </editor-fold>
}
