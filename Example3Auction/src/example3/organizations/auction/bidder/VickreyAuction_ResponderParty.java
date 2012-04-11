package example3.organizations.auction.bidder;

import example3.protocols.Protocols;
import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'Vickrey auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class VickreyAuction_ResponderParty extends ResponderParty<Bidder_Role> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new insatnce of the VickreyAuction_ResponderParty class.
     * @param message the ACL message initiating the protocol
     */
    public VickreyAuction_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry.getProtocol(Protocols.VICKREY_AUCTION_PROTOCOL), message);
    }
    
    // </editor-fold>
}
