package example3.protocols.vickreyauction;

import example3.organizations.auction.auctioneer.VickreyAuction_InitiatorParty;
import example3.organizations.auction.bidder.VickreyAuction_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.parties.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Vickrey auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class VickreyAuctionProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the VickreyAuctionProtocol class.
     */
    public VickreyAuctionProtocol() {
        super(ACLMessage.CFP);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Creates a new 'Vickrey auction' protocol initiator party.
     * @param arguments the 'Vickrey auction' protocol initiator party's contructor
     * arguments:
     *     - none
     * @returns a new 'Vickrey auction' protocol initiator party
     */
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new VickreyAuction_InitiatorParty();
    }

    /**
     * Creates a new 'Vickrey auction' protocol responder party.
     * @param message the ACL message to which the 'Vickrey auction' protocol
     * responder party responds
     * @returns a new 'Vickrey auction' protocol responder party
     */
    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new VickreyAuction_ResponderParty(message);
    }
    
    // </editor-fold>
}
