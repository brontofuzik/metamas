package example3.organizations.auction.auctioneer;

import example3.organizations.auction.auctioneer.auction.AuctionResult;
import example3.organizations.auction.auctioneer.auction.AuctionType;
import example3.organizations.auction.auctioneer.auction.AuctionArgument;
import example3.protocols.englishauction.EnglishAuctionProtocol;
import jade.core.AID;

/**
 * The 'English auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class EnglishAuction_InitiatorParty extends Auction_InitiatorParty {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String itemName;
    
    private double startingPrice;
    
    private double reservationPrice;
    
    private double bidIncrement;
    
    private boolean winnerDetermined;
    
    private double hammerPrice;
    
    private AID winnerAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public EnglishAuction_InitiatorParty() {
        super(EnglishAuctionProtocol.getInstance());
    }
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the auction type.
     * @return the auction type
     */
    @Override
    public AuctionType getAuctionType() {
        return AuctionType.ENGLISH;
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
        bidIncrement = argument.getBidChange();
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
