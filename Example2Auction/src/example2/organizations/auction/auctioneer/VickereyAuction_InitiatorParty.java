package example2.organizations.auction.auctioneer;

import example2.organizations.auction.auctioneer.auction.AuctionResult;
import example2.organizations.auction.auctioneer.auction.AuctionType;
import example2.organizations.auction.auctioneer.auction.AuctionArgument;
import example2.protocols.vickreyauction.VickreyAuctionProtocol;

/**
 * The 'Vickerey auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class VickereyAuction_InitiatorParty extends Auction_InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public VickereyAuction_InitiatorParty() {
        super(VickreyAuctionProtocol.getInstance());
    }    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the auction type.
     * @return the auction type
     */
    @Override
    public AuctionType getAuctionType() {
        return AuctionType.VICKREY;
    }
    
    /**
     * Sets the auction argument.
     * @param argument the auction argument
     */
    @Override
    public void setAuctionArgument(AuctionArgument argument) {
        // TODO Implement.
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Gets the auction result
     * @return the auction result
     */
    @Override
    public AuctionResult getAuctionResult() {
        // TODO Implement.
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
}
