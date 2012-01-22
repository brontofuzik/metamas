package example2.organizations.auction.auctioneer;

import example2.protocols.dutchauction.DutchAuctionProtocol;
import jade.core.AID;
import jadeorg.proto.InitiatorParty;

/**
 * The 'Dutch auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Auctioneer_DutchAuctionInitiator extends InitiatorParty
    implements AuctionInitiator {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String itemName;
    
    private double startingPrice;
    
    private double reservationPrice;
    
    private double bidDecrement;
    
    private boolean winnerDetermined;
    
    private double finalPrice;
    
    private AID winnerAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Auctioneer_DutchAuctionInitiator() {
        super(DutchAuctionProtocol.getInstance());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the type of the auction.
     * @return the type of the auction
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
        return new AuctionResult(winnerDetermined, winnerAID, finalPrice);
    }
    
    // </editor-fold>
}
