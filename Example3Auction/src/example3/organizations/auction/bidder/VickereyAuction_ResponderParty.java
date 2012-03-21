package example3.organizations.auction.bidder;

import example3.protocols.Protocols;
import jade.lang.acl.ACLMessage;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.ResponderParty;

/**
 * The 'Vickerey auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class VickereyAuction_ResponderParty extends ResponderParty<Bidder_Role> {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public VickereyAuction_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry_StaticClass.getProtocol(Protocols.VICKREY_AUCTION_PROTOCOL), message);
    }
    
    // </editor-fold>
}
