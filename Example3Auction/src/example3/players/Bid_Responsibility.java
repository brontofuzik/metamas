package example3.players;

import example3.players.bid.BidArgument;
import example3.players.bid.BidResult;
import thespian4jade.core.player.responsibility.AsynchronousResponsibility;

/**
 * The 'Bid' (asynchronous) responsibility.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Bid_Responsibility extends AsynchronousResponsibility<BidArgument, BidResult> {

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the owner participant.
     * @return the owner participant
     */
    private ParticipantPlayer getMyParticipant() {
        return (ParticipantPlayer)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        switch (getArgument().getAuctionType()) {
            case ENGLISH:
                setResult(bidEnglish(getArgument()));
                break;
                
            case DUTCH:
                setResult(bidDutch(getArgument()));
                break;
                
            case ENVELOPE:
                setResult(bidEnvelope(getArgument()));
                break;
                
            case VICKREY:
                setResult(bidVickrey(getArgument()));
                break;
        }
    }
    
    // ----- PRIVATE -----
    
    /**
     * Bids in an English auction.
     * @param argument the bid argument
     * @return the bid result
     */
    private BidResult bidEnglish(BidArgument argument) {
        Item item = getMyParticipant().getItemToBuy(argument.getItemName());
        double bid = argument.getCurrentPrice() + argument.getBidChange();      
        return (bid <= item.getPrice()) ?
            BidResult.createPositiveBidResult(bid) :
            BidResult.createNegativeBidResult();
    }
    
    /**
     * Bids in a Dutch auction.
     * @param argument the bid argument
     * @return the bid result
     */
    private BidResult bidDutch(BidArgument argument) {
        Item item = getMyParticipant().getItemToBuy(argument.getItemName());      
        return (argument.getCurrentPrice() <= item.getPrice()) ?
            BidResult.createPositiveBidResult(argument.getCurrentPrice()) :
            BidResult.createNegativeBidResult();
    }
    
    /**
     * Bids in an Envelope auction.
     * @param argument the bid argument
     * @return the bid result
     */
    private BidResult bidEnvelope(BidArgument argument) {
        Item item = getMyParticipant().getItemToBuy(argument.getItemName());
        return BidResult.createPositiveBidResult(item.getPrice());
    }
    
    /**
     * Bids in a Vickrey auction
     * @param argument the bid argument
     * @return the bid result
     */
    private BidResult bidVickrey(BidArgument argument) {
        Item item = getMyParticipant().getItemToBuy(argument.getItemName());
        return BidResult.createPositiveBidResult(item.getPrice());
    }
      
    // </editor-fold>
}
