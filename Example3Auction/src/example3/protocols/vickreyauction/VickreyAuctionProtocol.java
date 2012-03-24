package example3.protocols.vickreyauction;

import example3.organizations.auction.auctioneer.VickereyAuction_InitiatorParty;
import example3.organizations.auction.bidder.VickereyAuction_ResponderParty;
import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.InitiatorParty;
import thespian4jade.protocols.Protocol;
import thespian4jade.protocols.ResponderParty;

/**
 * The 'Vickerey auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class VickreyAuctionProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public InitiatorParty createInitiatorParty(Object[] arguments) {
        return new VickereyAuction_InitiatorParty();
    }

    @Override
    public ResponderParty createResponderParty(ACLMessage message) {
        return new VickereyAuction_ResponderParty(message);
    }
    
    // </editor-fold>
}
