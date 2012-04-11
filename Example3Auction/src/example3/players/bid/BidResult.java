package example3.players.bid;

import java.io.Serializable;

/**
 * A 'Bid' responsibility result.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class BidResult implements Serializable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final long serialVersionUID = 1L;
    
    /**
     * A flag indicating wheter a bid has been made.
     */
    private boolean bidMade;
    
    /**
     * The bid amount.
     */
    private double bidAmount;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the BidResult class.
     * @param bidMade a flag indicating wheter a bid has been made
     * @param bidAmount the bid amount 
     */
    private BidResult(boolean bidMade, double bidAmount) {
        // ----- Preconditions -----
        assert !bidMade || bidAmount > 0;
        // -------------------------
        
        this.bidMade = bidMade;
        this.bidAmount = bidAmount;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Determines whether a bid has been made.
     * @return <c>true</c> if the bid has been made, <c>false</c> otherwise
     */
    public boolean isBidMade() {
        return bidMade;
    }

    /**
     * Gets the bid amount.
     * @return the bid amount
     */
    public double getBidAmount() {
        // ----- Preconditions -----
        if (!bidMade) {
            throw new IllegalStateException("No bid. The bid has not been made.");
        }
        // -------------------------
        return bidAmount;
    }
    
   
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a positive (successful) bid result.
     * Design pattern: Static factory method
     * @param bid the bid
     * @return the positive bid result
     */
    public static BidResult createPositiveBidResult(double bid) {
        return new BidResult(true, bid);
    }
    
    /**
     * Creates a negative (unsuccessful) bid result.
     * Design pattern: Static factory method
     * @return the negative bid result
     */
    public static BidResult createNegativeBidResult() {
        return new BidResult(false, 0.0);
    }
    
    // </editor-fold>
}
