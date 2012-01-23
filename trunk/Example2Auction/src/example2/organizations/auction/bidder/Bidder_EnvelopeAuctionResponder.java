/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package example2.organizations.auction.bidder;

import example2.organizations.auction.Auction_Organization.Bidder_Role;
import example2.protocols.envelopeauction.EnvelopeAuctionProtocol;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.proto.Initialize;
import jadeorg.proto.ResponderParty;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

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
    
    /**
     * The flag indicating whether the bid has been made.
     */
    private boolean bidMade;
    
    /**
     * The bid.
     */
    private double bid;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Bidder_EnvelopeAuctionResponder class.
     * @param message 
     */
    public Bidder_EnvelopeAuctionResponder(ACLMessage message) {
        super(EnvelopeAuctionProtocol.getInstance(), message);
        
        auctioneer = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets my 'Bidder' role.
     * @return my 'Bidder' role.
     */
    private Bidder_Role getMyBidder() {
        return (Bidder_Role)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the FSM.
     */
    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
        State receiveAuctionCFP = new ReceiveAuctionCFP();
        State sendBid = new SendBid();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(receiveAuctionCFP);
        registerState(sendBid);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' state.
     * An (initial) state in which the party is initialized and begins.
     */
    private class MyInitialize extends Initialize {

        @Override
        protected int initialize() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * The 'Receive auction CFP' (single receiver) state.
     * A state in which the auciton call for proposals (CFP) is received
     * from the auctioneer.
     */
    private class ReceiveAuctionCFP extends SingleReceiverState {

        @Override
        protected AID getSenderAID() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected int onSingleReceiver() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void onEntry() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void onExit() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * The 'Send bid' (single-sender) state.
     * A state in which the bid is sent to the auctioneer.
     */
    private class SendBid extends SingleSenderState {

        @Override
        protected AID getReceiverAID() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void onSingleSender() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void onEntry() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        protected void onExit() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * The 'Success end' (one-shot) state.
     * A (final) state in which the party successfully ends.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        @Override
        public void action() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * The 'Failure end' (one-shot) state.
     * A (final) state in which the party unsuccessfully ends.
     */
    private class FailureEnd extends OneShotBehaviourState {

        @Override
        public void action() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    // </editor-fold>
}
