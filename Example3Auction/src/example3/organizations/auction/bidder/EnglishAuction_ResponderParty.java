package example3.organizations.auction.bidder;

import example3.protocols.Protocols;
import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.parties.ResponderParty;

/**
 * The 'English auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class EnglishAuction_ResponderParty extends ResponderParty<Bidder_Role> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new insatnce of the EnglishAuction_ResponderParty class.
     * @param message the ACL message initiating the protocol
     */
    public EnglishAuction_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry.getProtocol(Protocols.ENGLISH_AUCTION_PROTOCOL), message);
    }
    
    // </editor-fold>  
}
