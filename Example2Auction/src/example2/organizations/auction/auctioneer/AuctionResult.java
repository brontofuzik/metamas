package example2.organizations.auction.auctioneer;

import jade.core.AID;

/**
 * An 'Auction' power result.
 * @author Lukáš Kúdela
 * @since 2012-01-218
 * @version %I% %G%
 */
public class AuctionResult {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * A flag indicating whether a winner has been determined.
     */
    private boolean winnerDetermined;
    
    /**
     * The AID of the winner.
     */
    private AID winnerAID;
    
    /**
     * The final price.
     */
    private double finalPrice;
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public AuctionResult(boolean winnerDetermined,
        AID winnerAID, double finalPrice) {
        // ----- Preconditions -----
        assert !winnerDetermined || winnerAID != null;
        assert !winnerDetermined || finalPrice > 0;
        // -------------------------
        
        this.winnerAID = winnerAID;
        this.finalPrice = finalPrice;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public boolean isWinnerDetermined() {
        return winnerDetermined;
    }
    
    public double getFinalPrice() {
        // ----- Preconditions -----
        if (!winnerDetermined) {
            throw new IllegalStateException("No final price. The winner has not been determined.");
        }
        // -------------------------
        
        return finalPrice;
    }

    public AID getWinnerAID() {
        // ----- Preconditions -----
        if (!winnerDetermined) {
            throw new IllegalStateException("No winner AID. The winner has not been determined.");
        }
        // -------------------------
        
        return winnerAID;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a positive (successful) acution result.
     * Design pattern: Static factory method
     * @param winnerAID the AID of the auction winner
     * @param finalPrice the final price
     * @return the successful auction result
     */
    public static AuctionResult createPositiveAuctionResult(AID winnerAID,
        double finalPrice) {
        return new AuctionResult(true, winnerAID, finalPrice);
    }
    
    /**
     * Creates a negative (unsuccessful) auction result.
     * Design pattern: Static factory method
     * @return the unsuccessful auction result
     */
    public static AuctionResult createNegativeAuctionResult() {
        return new AuctionResult(false, null, 0.0);
    }
    
    // </editor-fold>
}
