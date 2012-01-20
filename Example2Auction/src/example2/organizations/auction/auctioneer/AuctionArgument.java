package example2.organizations.auction.auctioneer;

/**
 * An 'Auction' power argument.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class AuctionArgument {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The type of the auction.
     */
    private AuctionType auctionType;
    
    /**
     * The name of the auctioned item.
     */
    private String itemName;
    
    /**
     * The starting price.
     * The mandatory starting bid for a given auction, set by the seller at the
     * time of listing.
     */
    private double startingPrice;
    
    /**
     * The reservation price.
     * The minimum price a seller will accept for an item to be sold at auction.
     * This amount is never formally disclosed.
     */
    private double reservationPrice;
    
    /**
     * The bid increment.
     * The standardized amount an item increases in price after each new bid.
     * The auction service sets the increment, which rises according
     * to the present high bid value of an item.
     */
    private double bidChange;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public AuctionArgument(AuctionType auctionType, String itemName,
        double startingPrice, double reservationPrice, double bidChange) {
        // ---- Preconditions -----
        assert itemName != null && !itemName.isEmpty();
        assert startingPrice >= 0;
        // For the English auction, assert that the reservation price
        // is at least the starting price.
        assert auctionType != AuctionType.ENGLISH || reservationPrice >= startingPrice;
        // For the Dutch auction, assert that the reservation price
        // is at most the starting price.
        assert auctionType != AuctionType.DUTCH || reservationPrice <= startingPrice;
        // For the English auction, the change is an increment and
        // for the Dutch auction, the change is a decrement.
        assert bidChange > 0;
        // ------------------------
        
        this.auctionType = auctionType;
        this.itemName = itemName;
        this.startingPrice = startingPrice;
        this.reservationPrice = reservationPrice;
        this.bidChange = bidChange;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public AuctionType getAuctionType() {
        return auctionType;
    }

    public String getItemName() {
        return itemName;
    }

    public double getStartingPrice() {
        return startingPrice;
    }
    
    public double getReservationPrice() {
        return reservationPrice;
    }
    
    public double getBidChange() {
        return bidChange;
    }
    
    // </editor-fold>
}
