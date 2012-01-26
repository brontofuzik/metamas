package example2.organizations.auction.auctioneer;

import example2.protocols.englishauction.EnglishAuctionProtocol;
import jade.core.AID;
import jadeorg.proto.InitiatorParty;

/**
 * The 'English auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Auctioneer_EnglishAuctionInitiator extends InitiatorParty
    implements AuctionInitiator {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String itemName;
    
    private double startingPrice;
    
    private double reservationPrice;
    
    private double bidIncrement;
    
    private boolean winnerDetermined;
    
    private double finalPrice;
    
    private AID winnerAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Auctioneer_EnglishAuctionInitiator() {
        super(EnglishAuctionProtocol.getInstance());
    }
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the type of the auction.
     * @return the type of the auction
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
            AuctionResult.createPositiveAuctionResult(winnerAID, finalPrice) :
            AuctionResult.createNegativeAuctionResult();
    }
    
    // </editor-fold>
}
