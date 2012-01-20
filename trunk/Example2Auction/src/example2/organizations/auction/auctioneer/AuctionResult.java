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
    
    private boolean winnerDetermined;
    
    private double finalPrice;
    
    private AID winnerAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public AuctionResult(boolean winnerDetermined, double finalPrice,
        AID winnerAID) {
        // ----- Preconditions -----
        assert !winnerDetermined || finalPrice > 0;
        assert !winnerDetermined || winnerAID != null;
        // -------------------------
        
        this.finalPrice = finalPrice;
        this.winnerAID = winnerAID;
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
}
