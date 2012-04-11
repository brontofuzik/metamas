package example3.organizations.auction.auctioneer;

import example3.organizations.auction.auctioneer.auction.AuctionType;
import example3.protocols.Protocols;
import jade.core.AID;
import java.util.Map;
import thespian4jade.protocols.ProtocolRegistry;

/**
 * The 'Vickrey auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class VickreyAuction_InitiatorParty extends SealedBidAuction_InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the VickreyAuction_InitiatorParty class.
     */
    public VickreyAuction_InitiatorParty() {
        super(ProtocolRegistry.getProtocol(Protocols.VICKREY_AUCTION_PROTOCOL));
    }    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the auction type.
     * @return the auction type (Vickrey auction)
     */
    @Override
    public AuctionType getAuctionType() {
        return AuctionType.VICKREY;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Determines the winner and the hammer price.
     * @return <c>true</c> if the winner has been determined,
     *     <c>false</c> otherwise
     */
    @Override
    protected boolean determineWinner() {
        winner = null;
        double winnerPrice = Double.MIN_VALUE;
        hammerPrice = Double.MIN_VALUE;
        
        for (Map.Entry<AID, Double> entry : bids.entrySet()) {
            if (entry.getValue() > winnerPrice) {
                winner = entry.getKey();
                hammerPrice = winnerPrice;
                winnerPrice = entry.getValue();
            } else if (entry.getValue() > hammerPrice) {
                hammerPrice = entry.getValue();
            }
        }
        
        return hammerPrice >= reservationPrice;
    }
    
    // </editor-fold>
}
