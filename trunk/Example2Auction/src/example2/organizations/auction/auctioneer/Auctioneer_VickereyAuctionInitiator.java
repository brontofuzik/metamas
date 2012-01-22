package example2.organizations.auction.auctioneer;

import example2.protocols.vickreyauction.VickreyAuctionProtocol;
import jadeorg.proto.InitiatorParty;

/**
 * The 'Vickerey auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class Auctioneer_VickereyAuctionInitiator extends InitiatorParty
    implements AuctionInitiator {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Auctioneer_VickereyAuctionInitiator() {
        super(VickreyAuctionProtocol.getInstance());
    }    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the type of the auction.
     * @return the type of the auction
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
