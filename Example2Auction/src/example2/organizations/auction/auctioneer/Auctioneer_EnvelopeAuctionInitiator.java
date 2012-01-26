package example2.organizations.auction.auctioneer;

import example2.protocols.envelopeauction.AuctionCFPMessage;
import example2.protocols.envelopeauction.BidMessage;
import example2.protocols.envelopeauction.EnvelopeAuctionProtocol;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.Role;
import jadeorg.lang.Message;
import jadeorg.lang.SimpleMessage;
import jadeorg.proto.Initialize;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * The 'Envelope auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Luk� K�dela
 * @since 2012-01-21
 * @version %I% %G%
 */
public class Auctioneer_EnvelopeAuctionInitiator extends InitiatorParty
    implements AuctionInitiator {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The bidders. More precisely their AIDs.
     */
    private Set<AID> bidders = new HashSet<AID>();
    
    /**
     * The name of the item.
     */
    private String itemName;
    
    /**
     * The reservation price. Optional.
     */
    private Double reservationPrice;
    
    private Map<AID, Double> bids = new HashMap<AID, Double>();
    
    /**
     * A flag indicating whether the winner has been determined.
     */
    private boolean winnerDetermined;
    
    /**
     * The winner; more precisely its AID.
     */
    private AID winner;
    
    /**
     * The final price.
     */
    private double finalPrice;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Auctioneer_EnvelopeAuctionInitiator class.
     */
    public Auctioneer_EnvelopeAuctionInitiator() {
        super(EnvelopeAuctionProtocol.getInstance());
        buildFSM();
    }    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the type of the auction.
     * @return the type of the auction
     */
    @Override
    public AuctionType getAuctionType() {
        return AuctionType.ENVELOPE;
    }
    
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
     * Gets the auction result
     * @return the auction result
     */
    @Override
    public AuctionResult getAuctionResult() {
        return winnerDetermined ?
            AuctionResult.createPositiveAuctionResult(winner, finalPrice) :
            AuctionResult.createNegativeAuctionResult();
    }
    
    // ----- PRIVATE -----
    
    // TODO Consider moving this getter to the Party class.
    /**
     * Gets my role.
     * @return my role
     */
    private Role getMyRole() {
        return (Role)myAgent;
    }
    
    /**
     * Gets the bidders. More precisely, their AIDs.
     * @return the AIDs of the bidders
     */
    private AID[] getBidders() {
        AID[] bidders = new AID[this.bidders.size()];
        int i = 0;
        for (AID bidder : this.bidders) {
            bidders[i] = bidder;
            i++;
        }
        return bidders;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the FSM.
     */
    private void buildFSM() {
        // ----- States -----
        State initialize = new MyInitialize();
        State sendAuctionCFP = new SendAuctionCFP();
        State receiveBid = new ReceiveBid();
        State determineWinner = new DetermineWinner();
        State sendAuctionResultToWinner = new SendAuctionResultToWinner();
        State sendAuctionResultToLosers = new SendAuctionResultToLosers();
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
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
        receiveBid.registerTransition(ReceiveBid.SOME_BIDS_NOT_RECEIVED, receiveBid);
        
        determineWinner.registerTransition(DetermineWinner.WINNER_DETERMINED, sendAuctionResultToWinner);
        determineWinner.registerTransition(DetermineWinner.WINNER_NOT_DETERMINED, failureEnd);
        
        sendAuctionResultToWinner.registerDefaultTransition(sendAuctionResultToLosers);
        
        sendAuctionResultToLosers.registerDefaultTransition(successEnd);
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
            getMyRole().logInfo(String.format(
                "Initiating the 'Envelope auction' protocol (id = %1$s)",
                getProtocolId()));
            
            bidders = getMyRole().getMyOrganization().getAllRoleInstances("Bidder_Role");
            
            return OK;
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send auction CFP' (single sender) state.
     * A state in which the auction call for proposals (CFP) is sent
     * to the bidders.
     */
    private class SendAuctionCFP extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
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
        protected Message prepareMessage() {
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
            winner = null;
            finalPrice = Double.MIN_VALUE;
            for (Map.Entry<AID, Double> entry : bids.entrySet()) {
                if (entry.getValue() > finalPrice) {
                    winner = entry.getKey();
                    finalPrice = entry.getValue();
                }
            }
            
            exitValue = (finalPrice > reservationPrice) ?
                WINNER_DETERMINED :  WINNER_NOT_DETERMINED;
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
    private class SendAuctionResultToWinner extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            // TODO Implement.
            return new AID[] { winner };
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending auction result to the winner.");
        }
        
        /**
         * Creates the simple (AGREE) message.
         * @return the simple (AGREE) message
         */
        @Override
        protected Message prepareMessage() {
            // TODO Consider replacing the AGREE performative with ACCEPT_PROPOSAL.
            return new SimpleMessage(ACLMessage.AGREE);
        }

        @Override
        protected void onExit() {
            getMyRole().logInfo("Auction result sent to the winner.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Send auction result to the losers' (single sender) state.
     * A state in which the negative auction result is sent to the losers.
     */
    private class SendAuctionResultToLosers extends SingleSenderState {

        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        @Override
        protected AID[] getReceivers() {
            // TODO Implement.
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void onEntry() {
            getMyRole().logInfo("Sending auction result to the losers.");
        }
        
        /**
         * Prepares the simple (REFUSE) message.
         * @return the simple (REFUSE) message
         */
        @Override
        protected Message prepareMessage() {
            // TODO Consider replacing the REFUSE performative with REJECT_PROPOSAL.
            return new SimpleMessage(ACLMessage.REFUSE);
        }

        @Override
        protected void onExit() {
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
            
            getMyRole().logInfo("The 'Envelope auction' initiator succeeded.");
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
            
            getMyRole().logInfo("The 'Envelope auction' initiator party failed.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
