/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example2.organizations.auction.bidder;

import example2.protocols.envelopeauction.EnvelopeAuctionProtocol;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.ResponderParty;

/**
 * The 'Envelope auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class Bidder_EnvelopeAuctionResponder extends ResponderParty {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The aucitoneer. More precisely, its AID.
     */
    private AID auctioneer;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Bidder_EnvelopeAuctionResponder(ACLMessage message) {
        super(EnvelopeAuctionProtocol.getInstance(), message);
    }
    
    // </editor-fold>
}
