package example3.organizations.auction.auctioneer;

import example3.organizations.auction.auctioneer.auction.AuctionResult;
import example3.organizations.auction.auctioneer.auction.AuctionType;
import example3.organizations.auction.auctioneer.auction.AuctionArgument;
import thespian4jade.core.organization.Role;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;

/**
 * The 'Auction' abstract protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-01-19
 * @version %I% %G%
 */
public abstract class Auction_InitiatorParty extends InitiatorParty<Role> {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the AuctionInitiator class.
     * @param protocol 
     */
    protected Auction_InitiatorParty(Protocol protocol) {
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
    public static Auction_InitiatorParty createAuctionInitiator(AuctionType auctionType) {
        switch (auctionType) {
            case ENVELOPE:
                return new EnvelopeAuction_InitiatorParty();

            case VICKREY:
                return new VickreyAuction_InitiatorParty();

            case ENGLISH:
                return new EnglishAuction_InitiatorParty();

            case DUTCH:
                return new DutchAuction_InitiatorParty();

            default:
                return new EnvelopeAuction_InitiatorParty();
        }
    }
    
    // </editor-fold>
}
