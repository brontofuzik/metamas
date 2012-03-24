package example3.protocols.englishauction;

import example3.organizations.auction.auctioneer.EnglishAuction_InitiatorParty;
import example3.organizations.auction.bidder.EnglishAuction_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.protocols.ResponderParty;

/**
 * The 'English auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class EnglishAuctionProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Creates an initiator party.
     * @param arguments the initiator party's contructor arguments
     * @returns an initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EnglishAuction_InitiatorParty();
    }

    /**
     * Creates a responder party.
     * @param message the ACL message
     * @returns a responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EnglishAuction_ResponderParty(message);
    }

    // </editor-fold>
}
