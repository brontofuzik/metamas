package example3.organizations.auction.auctioneer;

import example3.organizations.auction.auctioneer.auction.AuctionResult;
import example3.organizations.auction.auctioneer.auction.AuctionType;
import example3.organizations.auction.auctioneer.auction.AuctionArgument;
import example3.protocols.Protocols;
import jade.core.AID;
import thespian4jade.protocols.ProtocolRegistry;

/**
 * The 'Dutch auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * Note: This class is currently not used.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class DutchAuction_InitiatorParty extends Auction_InitiatorParty {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String itemName;
    
    private double startingPrice;
    
    private double reservationPrice;
    
    private double bidDecrement;
    
    private boolean winnerDetermined;
    
    private double hammerPrice;
    
    private AID winnerAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public DutchAuction_InitiatorParty() {
        super(ProtocolRegistry.getProtocol(Protocols.DUTCH_AUCTION_PROTOCOL));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the auction type.
     * @return the auction type
     */
    @Override
    public AuctionType getAuctionType() {
        return AuctionType.DUTCH;
    }
    
    /**
     * Sets the auction argument.
     * @param argument the auction argument
     */
    @Override
    public void setAuctionArgument(AuctionArgument argument) {
        itemName = argument.getItemName();
        startingPrice = argument.getStartingPrice();
        reservationPrice = argument.getReservationPrice();
        bidDecrement = argument.getBidChange();
    }

    /**
     * Gets the auction result
     * @return the auction result
     */
    @Override
    public AuctionResult getAuctionResult() {
        return winnerDetermined ?
            AuctionResult.createPositiveAuctionResult(winnerAID, hammerPrice) :
            AuctionResult.createNegativeAuctionResult();
    }
    
    // </editor-fold>
}
