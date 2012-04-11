package example3.protocols.englishauction;

import example3.organizations.auction.auctioneer.EnglishAuction_InitiatorParty;
import example3.organizations.auction.bidder.EnglishAuction_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'English auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class EnglishAuctionProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EnglishAuctionProtocol class.
     */
    public EnglishAuctionProtocol() {
        super(ACLMessage.CFP);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Creates a new 'English auction' protocol initiator party.
     * @param arguments the 'English auction' protocol initiator party's contructor
     * arguments:
     *     - none
     * @returns a new 'English auction' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new EnglishAuction_InitiatorParty();
    }

    /**
     * Creates a new 'English auction' protocol responder party.
     * @param message the ACL message to which the 'English auction' protocol
     * responder party responds
     * @returns a new 'English auction' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new EnglishAuction_ResponderParty(message);
    }

    // </editor-fold>
}
