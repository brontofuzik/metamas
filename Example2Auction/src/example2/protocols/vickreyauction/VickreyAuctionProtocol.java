package example2.protocols.vickreyauction;

import example2.organizations.auction.auctioneer.VickereyAuction_InitiatorParty;
import example2.organizations.auction.bidder.VickereyAuction_ResponderParty;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;

/**
 * The 'Vickerey auction' protocol.
 * Design pattern: Singleton, Role: Singleton
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class VickreyAuctionProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static VickreyAuctionProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private VickreyAuctionProtocol() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static VickreyAuctionProtocol getInstance() {
        if (singleton == null) {
            singleton = new VickreyAuctionProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
    
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
