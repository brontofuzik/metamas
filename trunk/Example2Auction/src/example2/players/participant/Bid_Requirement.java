package example2.players.participant;

import jadeorg.core.player.requirement.OneShotRequirement;

/**
 * The 'Bid' (one-shot) requirement.
 * @author Luk� K�dela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Bid_Requirement extends OneShotRequirement<BidArgument, BidResult> {

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
        boolean bidMade = bid <= item.getPrice();
        return new BidResult(bidMade, bid);
    }
    
    /**
     * Bids in a Dutch auction.
     * @param argument the bid argument
     * @return the bid result
     */
    private BidResult bidDutch(BidArgument argument) {
        Item item = getMyParticipant().getItemToBuy(argument.getItemName());
        boolean bidMade = argument.getCurrentPrice() <= item.getPrice();
        return new BidResult(bidMade, argument.getCurrentPrice());
    }
    
    /**
     * Bids in an Envelope auction.
     * @param argument the bid argument
     * @return the bid result
     */
    private BidResult bidEnvelope(BidArgument argument) {
        Item item = getMyParticipant().getItemToBuy(argument.getItemName());
        return new BidResult(true, item.getPrice());
    }
    
    /**
     * Bids in a Vickrey auction
     * @param argument the bid argument
     * @return the bid result
     */
    private BidResult bidVickrey(BidArgument argument) {
        Item item = getMyParticipant().getItemToBuy(argument.getItemName());
        return new BidResult(true, item.getPrice());
    }
      
    // </editor-fold>
}