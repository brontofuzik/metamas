package example3.organizations.auction.auctioneer.auction;

import jade.core.AID;
import java.io.Serializable;

/**
 * An 'Auction' competence result.
 * @author Lukáš Kúdela
 * @since 2012-01-218
 * @version %I% %G%
 */
public class AuctionResult implements Serializable {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final long serialVersionUID = 1L;
    
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
    private double hammerPrice;
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the AuctionResult class.
     * @param winnerDetermined a flag indicating whether the winner has been determined
     * @param winnerAID the winner AID
     * @param hammerPrice the hammer price
     */
    private AuctionResult(boolean winnerDetermined,
        AID winnerAID, double hammerPrice) {
        // ----- Preconditions -----
        assert !winnerDetermined || winnerAID != null;
        assert !winnerDetermined || hammerPrice > 0;
        // -------------------------
        
        this.winnerAID = winnerAID;
        this.hammerPrice = hammerPrice;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public boolean isWinnerDetermined() {
        return winnerDetermined;
    }
    
    public double getHammerPrice() {
        // ----- Preconditions -----
        if (!winnerDetermined) {
            throw new IllegalStateException("No final price. The winner has not been determined.");
        }
        // -------------------------
        
        return hammerPrice;
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
     * @param hammerPrice the hammer price
     * @return the successful auction result
     */
    public static AuctionResult createPositiveAuctionResult(AID winnerAID,
        double hammerPrice) {
        return new AuctionResult(true, winnerAID, hammerPrice);
    }
    
    /**
     * Creates a negative (unsuccessful) auction result.
     * Design pattern: Static factory method
     * @return the unsuccessful auction result
     */
    public static AuctionResult createNegativeAuctionResult() {
        return new AuctionResult(false, null, 0.0);
    }
    
    @Override
    public String toString() {
        return "AuctionResult{" + "winnerDetermined=" + winnerDetermined
            + ", winnerAID=" + winnerAID + ", hammerPrice=" + hammerPrice + '}';
    }   
    
    // </editor-fold>
}
