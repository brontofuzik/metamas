package example2.organizations.auction.auctioneer;

import jadeorg.core.organization.Role;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;

/**
 * An auction initiator.
 * @author Lukáš Kúdela
 * @since 2012-01-19
 * @version %I% %G%
 */
public abstract class AuctionInitiator extends InitiatorParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the AuctionInitiator class.
     * @param protocol 
     */
    protected AuctionInitiator(Protocol protocol) {
        super(protocol);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the auction type.
     * @return the auction type
     */
    public abstract AuctionType getAuctionType();
    
    /**
     * Sets the auction argument.
     * @param argument the auction argument
     */
    public abstract void setAuctionArgument(AuctionArgument argument);
    
    /**
     * Gets the auction result
     * @return the auction result
     */
    public abstract AuctionResult getAuctionResult();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an auction initiator party of a given type.
     * @param auctionType the auction type
     * @return the auction initiator party of the given type
     */
    public static AuctionInitiator createAuctionInitiator(AuctionType auctionType) {
        switch (auctionType) {
            case ENVELOPE:
                return new Auctioneer_EnvelopeAuctionInitiator();

            case VICKREY:
                return new Auctioneer_VickereyAuctionInitiator();

            case ENGLISH:
                return new Auctioneer_EnglishAuctionInitiator();

            case DUTCH:
                return new Auctioneer_DutchAuctionInitiator();

            default:
                return new Auctioneer_EnvelopeAuctionInitiator();
        }
    }
    
    // </editor-fold>
}
