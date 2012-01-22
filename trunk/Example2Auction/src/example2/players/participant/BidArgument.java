package example2.players.participant;

import example2.organizations.auction.auctioneer.AuctionType;

/**
 * A 'Bid' requirement arguemnt.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class BidArgument {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
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
     */
    private double currentPrice;
    
    /**
     * The bid change.
     */
    private double bidChange;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public BidArgument(AuctionType auctionType, String itemName,
        double currentPrice, double bidChange) {
        // ----- Preconditions -----
        assert auctionType != null;
        assert itemName != null && !itemName.isEmpty();
        assert currentPrice > 0;
        assert bidChange > 0;
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
}
