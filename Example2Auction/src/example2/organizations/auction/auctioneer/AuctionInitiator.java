package example2.organizations.auction.auctioneer;

/**
 * An auction initiator.
 * @author Lukáš Kúdela
 * @since 2012-01-19
 * @version %I% %G%
 */
public interface AuctionInitiator {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the type of the auction.
     * @return the type of the auction
     */
    public AuctionType getAuctionType();
    
    /**
     * Sets the auction argument.
     * @param argument the auction argument
     */
    public void setAuctionArgument(AuctionArgument argument);
    
    /**
     * Gets the auction result
     * @return the auction result
     */
    public AuctionResult getAuctionResult();
    
    // </editor-fold>
}
