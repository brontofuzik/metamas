package example2.players.participant;

import example2.players.participant.bid.BidArgument;
import example2.players.participant.bid.BidResult;
import thespian4jade.core.player.requirement.OneShotRequirement;

/**
 * The 'Bid' (one-shot) responsibility.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Bid_Responsibility extends OneShotRequirement<BidArgument, BidResult> {

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    private Participant_Player getMyParticipant() {
        return (Participant_Player)myAgent;
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
