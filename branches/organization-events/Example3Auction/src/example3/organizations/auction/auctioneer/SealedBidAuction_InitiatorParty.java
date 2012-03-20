package example3.organizations.auction.auctioneer;

import example3.organizations.auction.auctioneer.auction.AuctionArgument;
import example3.organizations.auction.auctioneer.auction.AuctionResult;
import example3.protocols.envelopeauction.AuctionCFPMessage;
import example3.protocols.envelopeauction.BidMessage;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import thespian4jade.lang.SimpleMessage;
import thespian4jade.proto.Initialize;
import thespian4jade.proto.Protocol;
import thespian4jade.proto.SingleReceiverState;
import thespian4jade.proto.SingleSenderState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.IState;

/**
 * The 'Sealed bid auction' protocol initiator party.
 * @author Lukáš Kúdela
 * @since 2012-03-18
 * @version %I% %G%
 */
public abstract class SealedBidAuction_InitiatorParty extends Auction_InitiatorParty {

    // <editor-fold defaultstate="collapsed" desc="Fields">
        
    /**
     * The bidders; more precisely their AIDs.
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
        IState initialize = new MyInitialize();
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
        initialize.registerTransition(Initialize.OK, sendAuctionCFP);
        initialize.registerTransition(Initialize.FAIL, failureEnd);        
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
     * Determies the winner and the hammer price.
     * @return <c>true</c> if the winner has been determined; <c>false</c> otherwise.
     */
    protected abstract boolean determineWinner();
    
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
            getMyRole().logInfo(String.format(
                "Initiating the 'Envelope auction' protocol (id = %1$s)",
                getProtocolId()));
            
            bidders = getMyRole().getMyOrganization().getAllPositions("Bidder_Role");
            bidders.remove(getMyAgent().getAID());
            
            return OK;
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send auction CFP' (single sender) state.
     * A state in which the auction call for proposals (CFP) is sent
     * to the bidders.
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
            getMyRole().logInfo("Sending auction CFP.");
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
            getMyRole().logInfo("Auction CFP sent.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Receive bid' (single sender) state.
     * A state in which a bid is received from the bidders.
     */
    private class ReceiveBid extends SingleReceiverState<BidMessage> {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        /** An situation where all bids have been received. */
        public static final int ALL_BIDS_RECEIVED = 0;
        
        /** A situation where some bids have not been received. */
        public static final int SOME_BIDS_NOT_RECEIVED = 1;
              
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        ReceiveBid() {
            super(new BidMessage.Factory());
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
            getMyRole().logInfo("Receiving bid.");
        }
        
        /**
         * Hanles the 'Bid' message.
         * @param message the 'Bid' message
         */
        @Override
        protected void handleMessage(BidMessage message) {
            bids.put(message.getSender(), message.getBid());
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Bid received.");
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
     * A state in which the positive auction result is sent to the winner.
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
            getMyRole().logInfo("Sending auction result to the winner.");
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
            getMyRole().logInfo("Auction result sent to the winner.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send auction result to the losers' (single sender) state.
     * A state in which the negative auction result is sent to the losers.
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
            getMyRole().logInfo("Sending auction result to the losers.");
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
            getMyRole().logInfo("Auction result sent to the losers.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Success end' state.
     * A (final) state in which the party secceeds.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            winnerDetermined = true;
            
            // LOG
            getMyRole().logInfo("The '" + getAuctionType().getName() + "' initiator succeeded.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Failure end' state.
     * A (final) state in which the party fails.
     */
    private class FailureEnd extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            winnerDetermined = false;
            
            // LOG
            getMyRole().logInfo("The '" + getAuctionType().getName() + "' initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
