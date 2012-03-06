package example2.organizations.auction.bidder;

import example2.players.participant.bid.BidArgument;
import example2.players.participant.bid.BidResult;
import example2.protocols.envelopeauction.AuctionCFPMessage;
import example2.protocols.envelopeauction.BidMessage;
import example2.protocols.envelopeauction.EnvelopeAuctionProtocol;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.core.organization.Role;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.InvokeRequirementState;
import thespian4jade.proto.ReceiveAcceptOrRejectProposal;
import thespian4jade.proto.ResponderParty;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

/**
 * The 'Envelope auction' protocol responder party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class EnvelopeAuction_ResponderParty extends ResponderParty<Bidder_Role> {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The aucitoneer. More precisely, its AID.
     */
    private AID auctioneer;
    
    /**
     * The name of the item.
     */
    private String itemName;
    
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
     * @param message the ACL message
     */
    public EnvelopeAuction_ResponderParty(ACLMessage message) {
        super(EnvelopeAuctionProtocol.getInstance(), message);
        
        // TODO Consider moving this initialization to the 'MyInitialize' state.
        auctioneer = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the finite state machine, i. e. registers the states and transitions.
     */
    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
        State receiveAuctionCFP = new ReceiveAuctionCFP();
        State invokeRequirement_Bid = new InvokeRequirement_Bid();
        State sendBid = new SendBid();
        State receiveAuctionResult = new ReceiveAuctionResult();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(receiveAuctionCFP);
        registerState(invokeRequirement_Bid);
        registerState(sendBid);
        registerState(receiveAuctionResult);
        
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerTransition(Initialize.OK, receiveAuctionCFP);
        initialize.registerTransition(Initialize.FAIL, failureEnd);

        receiveAuctionCFP.registerDefaultTransition(invokeRequirement_Bid);
        
        invokeRequirement_Bid.registerDefaultTransition(sendBid);
        
        sendBid.registerDefaultTransition(receiveAuctionResult);
        
        receiveAuctionResult.registerTransition(ReceiveAuctionResult.ACCEPT_PROPOSAL, successEnd);
        receiveAuctionResult.registerTransition(ReceiveAuctionResult.REJECT_PROPOSAL, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' state.
     * An (initial) state in which the party is initialized and begins.
     */
    private class MyInitialize extends Initialize {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected int initialize() {
            getMyAgent().logInfo(String.format(
                "Responding to the 'Envelope auction' protocol (id = %1$s)",
                getProtocolId()));
            
            return OK;
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive auction CFP' (single receiver) state.
     * A state in which the auciton call for proposals (CFP) is received
     * from the auctioneer.
     */
    private class ReceiveAuctionCFP extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public void action() {
            getMyAgent().logInfo("Receiving auction CFP.");
            AuctionCFPMessage message = new AuctionCFPMessage();
            message.parseACLMessage(getACLMessage());
            
            itemName = message.getItemName();
            getMyAgent().logInfo("Auction CFP received.");
        }
       
        // </editor-fold>
    }
    
    /**
     * The 'Invoke requirement - Bid' state.
     * A state in which the 'Bid' requirement is invoked.
     */
    private class InvokeRequirement_Bid
        extends InvokeRequirementState<BidArgument, BidResult> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the InvokeRequirement_Bid class.
         */
        InvokeRequirement_Bid() {
            super("Bid_Requirement");
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">

        /**
         * Gets the 'Bid' requirement argument.
         * @return the 'Bid' requirement argument
         */
        @Override
        protected BidArgument getRequirementArgument() {
            return BidArgument.createEnvelopeBidArgument(itemName);
        }

        /**
         * Sets the 'Bid' requirement result.
         * @param requirementResult the 'Bid' requirement result
         */
        @Override
        protected void setRequirementResult(BidResult requirementResult) {
            bidMade = requirementResult.isBidMade();
            if (bidMade) {
                bid = requirementResult.getBid();
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send bid' (single-sender) state.
     * A state in which the bid is sent to the auctioneer.
     */
    private class SendBid extends SingleSenderState<BidMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            return new AID[] { auctioneer };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending bid.");
        }
        
        /**
         * Prepares the 'Bid' message.
         * @return the 'Bid' message
         */
        @Override
        protected BidMessage prepareMessage() {
            BidMessage message = new BidMessage();
            // TODO Also consider the situation when no bid is made.
            message.setBid(bid);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Bid sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive ACCEPT_PROPOSAL or REJECT_PROPOSAL' (multi-receiver) state.
     * A state in which the auction result (ACCEPT_PROPOSAL or REJECT_PROPOSAL) is received
     * from the auctioneer.
     */
    private class ReceiveAuctionResult extends ReceiveAcceptOrRejectProposal {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return new AID[] { auctioneer };
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving auction result.");
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Auction result received.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' (one-shot) state.
     * A (final) state in which the party succeeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">     
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Envelope auction' responder party succeeded.");
        }
        
        // </editor-fold> 
    }
    
    /**
     * The 'Failure end' (one-shot) state.
     * A (final) state in which the party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">     
        
        @Override
        public void action() {
            getMyAgent().logInfo("The 'Envelope auction' responder party failed.");
        }
        
        // </editor-fold> 
    }
    
    // </editor-fold>
}
