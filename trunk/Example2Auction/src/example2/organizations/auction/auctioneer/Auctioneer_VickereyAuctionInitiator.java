package example2.organizations.auction.auctioneer;

import example2.protocols.vickereyauction.VickereyAuctionProtocol;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;

/**
 * The 'Vickerey auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class Auctioneer_VickereyAuctionInitiator extends InitiatorParty {

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public Protocol getProtocol() {
        return VickereyAuctionProtocol.getInstance();
    } 
    
    // </editor-fold>
}
