package example2.organizations.auction.auctioner;

import example2.protocols.englishauctionprotocol.EnglishAuctionProtocol;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;

/**
 * The 'English auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-20
 * @version %I% %G%
 */
public class Auctioner_EnglishAuctionInitiator extends InitiatorParty {

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public Protocol getProtocol() {
        return EnglishAuctionProtocol.getInstance();
    }
    
    // </editor-fold>
}
