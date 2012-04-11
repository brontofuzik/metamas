package example3.organizations.auction.auctioneer;

import example3.organizations.auction.auctioneer.auction.AuctionArgument;
import example3.organizations.auction.auctioneer.auction.AuctionResult;
import example3.organizations.auction.bidder.Bidder_Role;
import example3.protocols.AuctionCFPMessage;
import example3.protocols.BidProposeMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import thespian4jade.core.organization.Role;
import thespian4jade.language.SimpleMessage;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.states.receiver.SingleReceiverState;
import thespian4jade.behaviours.states.sender.SingleSenderState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;

/**
 * The 'Sealed bid auction' abstract protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-18
 * @version %I% %G%
 */
public abstract class SealedBidAuction_InitiatorParty extends Auction_InitiatorParty {

    // <editor-fold defaultstate="collapsed" desc="Fields">
        
    /**
     * The bidders; more precisely their AIDs.
     * The responder parties.
     */
    private Set<AID> bidders = new HashSet<AID>();
    
    /**
     * The name of the item.
     */
    private String itemName;
    
    /**
     * The reservation price. Optional.
     */
    protected Double reservationPrice;
    
    /**
     * The bids.
     */
    protected Map<AID, Double> bids = new HashMap<AID, Double>();
    
    /**
     * A flag indicating whether the winner has been determined.
     */
    private boolean winnerDetermined;
    
    /**
     * The winner; more precisely its AID.
     */
    protected AID winner;
    
    /**
     * The hammer price.
     */
    protected double hammerPrice;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the SealedBidAuction_InitiatorParty class.
     */
    public SealedBidAuction_InitiatorParty(Protocol protocol) {
        super(protocol);
        buildFSM();
    }    

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the auction argument.
     * @param argument the auction argument
     */
    @Override
    public void setAuctionArgument(AuctionArgument argument) {
        itemName = argument.getItemName();
        reservationPrice = argument.getReservationPrice();
    }

    /**
     * Gets the auction result.
     * @return the auction result
     */
    @Override
    public AuctionResult getAuctionResult() {
        return winnerDetermined ?
            AuctionResult.createPositiveAuctionResult(winner, hammerPrice) :
            AuctionResult.createNegativeAuctionResult();
    }
    
    // ---------- PRIVATE ----------
    
    /**
     * Gets the bidders; more precisely, their AIDs.
     * @return the bidders; more precisely, their AIDs
     */
    private AID[] getBidders() {
        return bidders.toArray(new AID[bidders.size()]);
    }
    
    /**
     * Gets the losers; more precisely, their AIDs.
     * @return the losers; more precisely, their AIDs.
     */
    private AID[] getLosers() {        
        Set<AID> losers = new HashSet<AID>(bidders);
        losers.remove(winner);
        return losers.toArray(new AID[losers.size()]);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the party FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState initialize = new Initialize();
        IState sendAuctionCFP = new SendAuctionCFP();
        IState receiveBid = new ReceiveBid();
        IState determineWinner = new DetermineWinner();
        IState sendAuctionResultToWinner = new SendAuctionResultToWinner();
        IState sendAuctionResultToLosers = new SendAuctionResultToLosers();
        IState successEnd = new SuccessEnd();
        IState failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        registerState(sendAuctionCFP);
        registerState(receiveBid);
        registerState(determineWinner);
        registerState(sendAuctionResultToWinner);
        registerState(sendAuctionResultToLosers);     
        registerLastState(successEnd);
        registerLastState(failureEnd);
        
        // Register the transitions.
        initialize.registerDefaultTransition(sendAuctionCFP);      
        sendAuctionCFP.registerDefaultTransition(receiveBid);       
        receiveBid.registerTransition(ReceiveBid.ALL_BIDS_RECEIVED, determineWinner);
        receiveBid.registerTransition(ReceiveBid.SOME_BIDS_NOT_RECEIVED, receiveBid,
            new String[] { receiveBid.getName() });       
        determineWinner.registerTransition(DetermineWinner.WINNER_DETERMINED, sendAuctionResultToWinner);
        determineWinner.registerTransition(DetermineWinner.WINNER_NOT_DETERMINED, failureEnd);       
        sendAuctionResultToWinner.registerDefaultTransition(sendAuctionResultToLosers);        
        sendAuctionResultToLosers.registerDefaultTransition(successEnd);
    }
    
    /**
     * Determines the winner and the hammer price.
     * @return <c>true</c> if the winner has been determined,
     *     <c>false</c> otherwise
     */
    protected abstract boolean determineWinner();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' initial (one-shot) state.
     * A state in which the party is initialized.
     */
    private class Initialize extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            getMyAgent().logInfo(String.format(
                "Initiating the 'Envelope auction' protocol (id = %1$s)",
                getProtocolId()));
            
            // Get all active 'Bidder' positions.
            List<Role> bidderPositions = getMyAgent().getMyOrganization()
                .getAllActivePositions(Bidder_Role.NAME);
            for (Role bidderPosition : bidderPositions) {
                if (bidderPosition != getMyAgent()) {
                    bidders.add(bidderPosition.getAID());
                }
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send auction call-for-proposal' (single sender) state.
     * A state in which the 'Auction call-for-proposal' message is sent.
     */
    private class SendAuctionCFP extends SingleSenderState<AuctionCFPMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the receivers; more precisely, their AIDs.
         * @return the receivers; more precisely, their AIDs.
         */
        @Override
        protected AID[] getReceivers() {
            return getBidders();
        }
   
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Sending auction CFP.");
        }

        /**
         * Prepares the 'Auction CFP' message.
         * @return the 'Auction CFP' message
         */
        @Override
        protected AuctionCFPMessage prepareMessage() {
            AuctionCFPMessage message = new AuctionCFPMessage();
            message.setItemName(itemName);
            return message;
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Auction CFP sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive bid' (single receiver) state.
     * A state in which the 'Bid propose' message is received.
     */
    private class ReceiveBid extends SingleReceiverState<BidProposeMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        /** An situation where all bids have been received. */
        public static final int ALL_BIDS_RECEIVED = 0;
        
        /** A situation where some bids have not been received. */
        public static final int SOME_BIDS_NOT_RECEIVED = 1;
              
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveBid() {
            super(new BidProposeMessage.Factory());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getSenders() {
            return getBidders();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        public int onEnd() {
            if (bids.size() == bidders.size()) {
                return ALL_BIDS_RECEIVED;
            } else {
                return SOME_BIDS_NOT_RECEIVED;
            }
        }
        
        // ----- PROTECTED -----
        
        @Override
        protected void onEntry() {
            getMyAgent().logInfo("Receiving bid.");
        }
        
        /**
         * Hanles the 'Bid' message.
         * @param message the 'Bid' message
         */
        @Override
        protected void handleMessage(BidProposeMessage message) {
            bids.put(message.getSender(), message.getBidAmount());
        }

        @Override
        protected void onExit() {
            getMyAgent().logInfo("Bid received.");
        }
        
        // </editor-fold> 
    }
    
    /**
     * The 'Determine winner' (one-shot) state.
     * A state in which the winner is determined.
     */
    private class DetermineWinner extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        /** The winner has been determined. */
        public static final int WINNER_DETERMINED = 0;
        
        /** The winner has not been determined. */
        public static final int WINNER_NOT_DETERMINED = 1;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private int exitValue;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            exitValue = determineWinner() ? WINNER_DETERMINED : WINNER_NOT_DETERMINED;
        }
        
        @Override
        public int onEnd() {
            return exitValue;
        }
            
        // </editor-fold>
    }
    
    /**
     * The 'Send auction result to the winner' (single sender) state.
     * A state in which the ACCEPT-PROPOSAL simple message is sent to the winner.
     */
    private class SendAuctionResultToWinner extends SingleSenderState<SimpleMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the receivers; more precisely, their AIDs.
         * @return the receivers; more precisely, their ADIs.
         */
        @Override
        protected AID[] getReceivers() {
            return new AID[] { winner };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Sending auction result to the winner.");
        }
        
        /**
         * Prepares the simple (AGREE) message.
         * @return the simple (AGREE) message
         */
        @Override
        protected SimpleMessage prepareMessage() {
            return new SimpleMessage(ACLMessage.ACCEPT_PROPOSAL);
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Auction result sent to the winner.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send auction result to the losers' (single sender) state.
     * A state in which the REJECT-PROPOSAL simple message is sent to the losers.
     */
    private class SendAuctionResultToLosers extends SingleSenderState<SimpleMessage> {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        /**
         * Gets the receivers; more precisely, their AIDs.
         * @return the receivers; more precisely, their ADIs.
         */
        @Override
        protected AID[] getReceivers() {
            return getLosers();
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            // LOG
            getMyAgent().logInfo("Sending auction result to the losers.");
        }
        
        /**
         * Prepares the simple (REFUSE) message.
         * @return the simple (REFUSE) message
         */
        @Override
        protected SimpleMessage prepareMessage() {
            return new SimpleMessage(ACLMessage.REJECT_PROPOSAL);
        }

        @Override
        protected void onExit() {
            // LOG
            getMyAgent().logInfo("Auction result sent to the losers.");
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
            winnerDetermined = true;
            
            // LOG
            getMyAgent().logInfo("The '" + getAuctionType().getName() + "' initiator succeeded.");
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
            winnerDetermined = false;
            
            // LOG
            getMyAgent().logInfo("The '" + getAuctionType().getName() + "' initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}