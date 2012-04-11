package example3.organizations.auction.auctioneer.auction;

import java.io.Serializable;

/**
 * An 'Auction' competence argument.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class AuctionArgument implements Serializable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final long serialVersionUID = 1L;
    
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
     * Only applicable to the English and Dutch auctions.
     */
    private Double startingPrice;
    
    /**
     * The reservation price.
     * The minimum price a seller will accept for an item to be sold at auction.
     * This amount is never formally disclosed.
     * Optional.
     */
    private Double reservationPrice;
    
    /**
     * The bid increment.
     * The standardized amount an item increases in price after each new bid.
     * The auction service sets the increment, which rises according
     * to the present high bid value of an item.
     * Only applicable to the English and Dutch auctions.
     */
    private Double bidChange;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the AuctionArgument class.
     * @param auctionType the auction type
     * @param itemName the name of the item
     * @param startingPrice the starting price
     * @param reservationPrice the reservation price
     * @param bidChange the bid change
     */
    private AuctionArgument(AuctionType auctionType, String itemName,
        Double startingPrice, Double reservationPrice, Double bidChange) {
        // ---- Preconditions -----
        assert itemName != null && !itemName.isEmpty();
        assert startingPrice == null || startingPrice >= 0;
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

    public Double getStartingPrice() {
        return startingPrice;
    }
    
    public Double getReservationPrice() {
        return reservationPrice;
    }
    
    public Double getBidChange() {
        return bidChange;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an auction argument for the English auction.
     * Design pattern: Static factory method
     * @param itemName the name of the item
     * @param startingPrice the starting price
     * @param reservationPrice the reservation price
     * @param bidIncrement the bid increment
     * @return the auction argument for the English auction
     */
    public static AuctionArgument createEnglishAuctionArgument(String itemName,
        double startingPrice, Double reservationPrice, double bidIncrement) {
        return new AuctionArgument(AuctionType.ENGLISH, itemName,
            new Double(startingPrice), reservationPrice, new Double(bidIncrement));
    }
 
    /**
     * Creates an auction argument for the Dutch auction.
     * Design pattern: Static factory method
     * @param itemName the name of the item
     * @param startingPrice the starting price
     * @param reservationPrice the reservation price
     * @param bidDecrement the bid decrement
     * @return the auction argument for the Dutch auction
     */
    public static AuctionArgument createDutchAuctionArgument(String itemName,
        double startingPrice, Double reservationPrice, double bidDecrement) {
        return new AuctionArgument(AuctionType.DUTCH, itemName,
            new Double(startingPrice), reservationPrice, new Double(bidDecrement));
    }
    
    /**
     * Creates an auction argument for the Envelope auction.
     * Design pattern: Static factory method
     * @param itemName the name of the item
     * @param reservationPrice the reservation pice
     * @return the acution argument for the Envelope auction
     */
    public static AuctionArgument createEnvelopeAuctionArgument(String itemName,
        Double reservationPrice) {
        return new AuctionArgument(AuctionType.ENVELOPE, itemName, null,
            reservationPrice, null);
    }
    
    /**
     * Creates an auction argument for the Vickrey auction.
     * Design pattern: Static factory method
     * @param itemName the name of the item
     * @param reservationPrice the reservation pice
     * @return the acution argument for the Vickrey auction
     */
    public static AuctionArgument createVickreyAuctionArgument(String itemName,
        Double reservationPrice) {
        return new AuctionArgument(AuctionType.VICKREY, itemName, null,
            reservationPrice, null);
    }
    
    @Override
    public String toString() {
        return "AuctionArgument{" + "auctionType=" + auctionType + ", itemName=" + itemName
            + ", startingPrice=" + startingPrice + ", reservationPrice="
            + reservationPrice + ", bidChange=" + bidChange + '}';
    }
    
    // </editor-fold> 
}
