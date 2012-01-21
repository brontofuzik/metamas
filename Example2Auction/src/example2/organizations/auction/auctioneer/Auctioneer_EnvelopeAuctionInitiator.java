package example2.organizations.auction.auctioneer;

import example2.protocols.envelopeauction.EnvelopeAuctionProtocol;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.Protocol;

/**
 * The 'Envelope auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class Auctioneer_EnvelopeAuctionInitiator extends InitiatorParty {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Auctioneer_EnvelopeAuctionInitiator() {
        super(EnvelopeAuctionProtocol.getInstance());
    }    
    
    // </editor-fold>
}
