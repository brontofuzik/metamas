package example3.protocols.dutchauction;

import example3.organizations.auction.auctioneer.DutchAuction_InitiatorParty;
import example3.organizations.auction.bidder.DutchAuction_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Dutch auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class DutchAuctionProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the DutchAuctionProtocol class.
     */
    public DutchAuctionProtocol() {
        super(ACLMessage.CFP);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Creates a new 'Dutch auction' protocol initiator party.
     * @param arguments the 'Dutch auction' protocol initiator party's contructor
     * arguments:
     *     - none
     * @returns a new 'Dutch auction' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new DutchAuction_InitiatorParty();
    }

    /**
     * Creates a new 'Dutch auction' protocol responder party.
     * @param message the ACL message to which the 'Dutch auction' protocol
     * responder party responds
     * @returns a new 'Dutch auction' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new DutchAuction_ResponderParty(message);
    }

    // </editor-fold>
}
