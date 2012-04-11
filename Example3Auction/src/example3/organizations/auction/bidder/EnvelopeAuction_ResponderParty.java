package example3.organizations.auction.bidder;

import example3.players.bid.BidArgument;
import example3.players.bid.BidResult;
import example3.protocols.Protocols;
import example3.protocols.AuctionCFPMessage;
import example3.protocols.BidProposeMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import thespian4jade.behaviours.states.special.InvokeResponsibilityState;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.states.receiver.ReceiveAcceptOrRejectProposal;
import thespian4jade.behaviours.parties.ResponderParty;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;

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
     * The auctioneer; more precisely, its AID.
     * The initiator party.
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
     * The bid amount.
     */
    private double bidAmount;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new insatnce of the EnvelopeAuction_ResponderParty class.
     * @param message the ACL message initiating the protocol
     */
    public EnvelopeAuction_ResponderParty(ACLMessage message) {
        super(ProtocolRegistry.getProtocol(Protocols.ENVELOPE_AUCTION_PROTOCOL), message);
        
        // TODO (priority: low) Consider moving this initialization to the 'MyInitialize' state.
        auctioneer = getACLMessage().getSender();
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new Start();
        IState receiveAuctionCFP = new ReceiveAuctionCFP();
        IState invokeResponsibility_Bid = new InvokeResponsibility_Bid();
        IState sendBid = new SendBid();
        IState receiveAuctionResult = new ReceiveAuctionResult();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);       
        registerState(receiveAuctionCFP);
        registerState(invokeResponsibility_Bid);
        registerState(sendBid);
        registerState(receiveAuctionResult);     
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerDefaultTransition(receiveAuctionCFP);
        receiveAuctionCFP.registerDefaultTransition(invokeResponsibility_Bid);       
        invokeResponsibility_Bid.registerDefaultTransition(sendBid);        
        sendBid.registerDefaultTransition(receiveAuctionResult);        
        receiveAuctionResult.registerTransition(ReceiveAuctionResult.ACCEPT_PROPOSAL, successEnd);
        receiveAuctionResult.registerTransition(ReceiveAuctionResult.REJECT_PROPOSAL, failureEnd);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' initial (one-shot) state.
     * A state in which the party is initialized.
     */
    private class Start extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        // ----- Exit values -----
        public static final int OK = 1;
        public static final int FAIL = 2;
        // -----------------------
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo(String.format(
                "'Envelope auction' protocol (id = %1$s) responder party started.",
                getProtocolId()));
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive auction call-for-proposal' (single receiver) state.
     * A state in which the 'Auciton call-for-proposal' message is received.
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
     * The 'Invoke responsibility - Bid' (invoke responsibility) state.
     * A state in which the 'Bid' responsibility is invoked.
     */
    private class InvokeResponsibility_Bid
        extends InvokeResponsibilityState<BidArgument, BidResult> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the InvokeResponsibility_Bid class.
         */
        InvokeResponsibility_Bid() {
            super(Bidder_Role.BID_RESPONSIBILITY);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">

        /**
         * Gets the 'Bid' responsibility argument.
         * @return the 'Bid' responsibility argument
         */
        @Override
        protected BidArgument getResponsibilityArgument() {
            return BidArgument.createEnvelopeBidArgument(itemName);
        }

        /**
         * Sets the 'Bid' responsibility result.
         * @param responsibilityResult the 'Bid' responsibility result
         */
        @Override
        protected void setResponsibilityResult(BidResult responsibilityResult) {
            bidMade = responsibilityResult.isBidMade();
            if (bidMade) {
                bidAmount = responsibilityResult.getBidAmount();
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send bid' (single sender) state.
     * A state in which the 'Bid propose' message is sent.
     */
    private class SendBid extends SingleSenderState<BidProposeMessage> {

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
        protected BidProposeMessage prepareMessage() {
            BidProposeMessage message = new BidProposeMessage();
            // TODO (priority: low) Also consider the situation when no bid is made.
            message.setBid(bidAmount);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Bid sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive auction result' (receive-accept-or-reject-proposal) state.
     * A state in which the either the ACCEPT-PROPOSAL or REJECT-PROPOSAL simple
     * message is received.
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
     * The 'Success end' final (one-shot) state.
     * A state in which the party succeeds.
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
     * The 'Failure end' final (one-shot) state.
     * A state in which the party fails.
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
