package example2.organizations.auction.auctioneer;

import example2.protocols.englishauction.EnglishAuctionProtocol;
import jade.core.AID;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;

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
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public Protocol getProtocol() {
        return EnglishAuctionProtocol.getInstance();
    }
    
    @Override
    public void setAuctionArgument(AuctionArgument argument) {
        itemName = argument.getItemName();
        startingPrice = argument.getStartingPrice();
        reservationPrice = argument.getReservationPrice();
        bidIncrement = argument.getBidChange();
    }

    @Override
    public AuctionResult getAuctionResult() {
        return new AuctionResult(winnerDetermined, finalPrice, winnerAID);
    }
    
    // </editor-fold>
}
