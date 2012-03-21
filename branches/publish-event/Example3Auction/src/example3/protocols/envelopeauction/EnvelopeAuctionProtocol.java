package example3.protocols.envelopeauction;

import example3.organizations.auction.auctioneer.EnvelopeAuction_InitiatorParty;
import example3.organizations.auction.bidder.EnvelopeAuction_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.InitiatorParty;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.ResponderParty;

/**
 * The 'Envelope auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class EnvelopeAuctionProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The singleton instance of the EnvelopeAuctionProtocol class.
     */
    private static EnvelopeAuctionProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EnvelopeAuctionProtocol class.
     * The constructor is prive to prevent the direct instantiation of the class.
     */
    private EnvelopeAuctionProtocol() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the singleton instance of the EnvelopeAuctionProtocol class.
     * @return the singleton instance of the EnvelopAuctionProtocol class
     */
    public static EnvelopeAuctionProtocol getInstance() {
        if (singleton == null) {
            singleton = new EnvelopeAuctionProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EnvelopeAuction_InitiatorParty();
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EnvelopeAuction_ResponderParty(message);
    }
    
    // </editor-fold>
}
