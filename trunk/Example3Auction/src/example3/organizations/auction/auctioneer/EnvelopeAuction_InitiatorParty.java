package example3.organizations.auction.auctioneer;

import example3.organizations.auction.auctioneer.auction.AuctionType;
import example3.protocols.Protocols;
import jade.core.AID;
import java.util.Map;
import thespian4jade.protocols.ProtocolRegistry;

/**
 * The 'Envelope auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class EnvelopeAuction_InitiatorParty extends SealedBidAuction_InitiatorParty {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EnvelopeAuction_InitiatorParty class.
     */
    public EnvelopeAuction_InitiatorParty() {
        super(ProtocolRegistry.getProtocol(Protocols.ENVELOPE_AUCTION_PROTOCOL));
    }    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the auction type.
     * @return the auction type (Enveope auction)
     */
    @Override
    public AuctionType getAuctionType() {
        return AuctionType.ENVELOPE;
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
        hammerPrice = Double.MIN_VALUE;
        
        for (Map.Entry<AID, Double> entry : bids.entrySet()) {
            if (entry.getValue() > hammerPrice) {
                winner = entry.getKey();
                hammerPrice = entry.getValue();
            }
        }
        
        return hammerPrice >= reservationPrice;
    }
    
    // </editor-fold>
}
