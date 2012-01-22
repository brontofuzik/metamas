package example2.organizations.auction.auctioneer;

import example2.protocols.envelopeauction.EnvelopeAuctionProtocol;
import jade.core.AID;
import jadeorg.proto.InitiatorParty;
import java.util.HashSet;
import java.util.Set;

/**
 * The 'Envelope auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class Auctioneer_EnvelopeAuctionInitiator extends InitiatorParty
    implements AuctionInitiator {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The bidders. More precisely their AIDs.
     */
    private Set<AID> bidders = new HashSet<AID>();
    
    /**
     * The name of the item.
     */
    private String itemName;
    
    /**
     * The reservation price. Optional.
     */
    private Double reservationPrice;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Auctioneer_EnvelopeAuctionInitiator() {
        super(EnvelopeAuctionProtocol.getInstance());
    }    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the type of the auction.
     * @return the type of the auction
     */
    @Override
    public AuctionType getAuctionType() {
        return AuctionType.ENVELOPE;
    }
    
    /**
     * Sets the auction argument.
     * @param argument the auction argument
     */
    @Override
    public void setAuctionArgument(AuctionArgument argument) {
        itemName = argument.getItemName();
        reservationPrice = argument.getReservationPrice();
    }

    /**
     * Gets the auction result
     * @return the auction result
     */
    @Override
    public AuctionResult getAuctionResult() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
}
