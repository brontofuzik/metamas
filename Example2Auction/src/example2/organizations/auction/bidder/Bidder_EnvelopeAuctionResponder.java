/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example2.organizations.auction.bidder;

import example2.protocols.envelopeauction.EnvelopeAuctionProtocol;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.Protocol;
import jadeorg.proto.ResponderParty;

/**
 * The 'Envelope auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class Bidder_EnvelopeAuctionResponder extends ResponderParty {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Bidder_EnvelopeAuctionResponder(ACLMessage message) {
        super(message);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public Protocol getProtocol() {
        return EnvelopeAuctionProtocol.getInstance();
    }
    
    // </editor-fold>
}
