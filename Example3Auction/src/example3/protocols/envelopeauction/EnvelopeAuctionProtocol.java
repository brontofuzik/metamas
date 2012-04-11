package example3.protocols.envelopeauction;

import example3.organizations.auction.auctioneer.EnvelopeAuction_InitiatorParty;
import example3.organizations.auction.bidder.EnvelopeAuction_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Envelope auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class EnvelopeAuctionProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EnvelopeAuctionProtocol class.
     */
    public EnvelopeAuctionProtocol() {
        super(ACLMessage.CFP);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Envelope auction' protocol initiator party.
     * @param arguments the 'Envelope auction' protocol initiator party's contructor
     * arguments:
     *     - none
     * @returns a new 'Envelope auction' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EnvelopeAuction_InitiatorParty();
    }

    /**
     * Creates a new 'Envelope auction' protocol responder party.
     * @param message the ACL message to which the 'Envelope auction' protocol
     * responder party responds
     * @returns a new 'Envelope auction' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EnvelopeAuction_ResponderParty(message);
    }
    
    // </editor-fold>
}
