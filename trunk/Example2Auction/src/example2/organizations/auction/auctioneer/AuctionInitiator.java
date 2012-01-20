package example2.organizations.auction.auctioneer;

/**
 * An auction initiator.
 * @author Lukáš Kúdela
 * @since 2012-01-19
 * @version %I% %G%
 */
public interface AuctionInitiator {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public void setAuctionArgument(AuctionArgument argument);
    
    public AuctionResult getAuctionResult();
    
    // </editor-fold>
}
