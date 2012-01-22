/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example2.organizations.auction.bidder;

import example2.protocols.vickreyauction.VickreyAuctionProtocol;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.ResponderParty;

/**
 * The 'Vickerey auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class Bidder_VickereyAuctionResponder extends ResponderParty {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Bidder_VickereyAuctionResponder(ACLMessage message) {
        super(VickreyAuctionProtocol.getInstance(), message);
    }
    
    // </editor-fold>
}
