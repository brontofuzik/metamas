package example3.protocols.envelopeauction;

import example3.organizations.auction.auctioneer.EnvelopeAuction_InitiatorParty;
import example3.organizations.auction.bidder.EnvelopeAuction_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.protocols.ResponderParty;

/**
 * The 'Envelope auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class EnvelopeAuctionProtocol extends Protocol {
    
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
