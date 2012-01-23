package example2.organizations.auction.auctioneer;

import example2.organizations.auction.Auction_Organization.Auctioneer_Role;
import example2.protocols.envelopeauction.EnvelopeAuctionProtocol;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jadeorg.proto.Initialize;
import jadeorg.proto.InitiatorParty;
import jadeorg.proto.SingleReceiverState;
import jadeorg.proto.SingleSenderState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import java.util.HashSet;
import java.util.Set;

/**
 * The 'Envelope auction' protocol initiator party.
 * Design pattern: Abstract factory, Role: Concrete product
 * @author Lukáš Kúdela
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
    
    /**
     * A flag indicating whether the winner has been determined.
     */
    private boolean winnerDetermined;
    
    /**
     * The AID of the winner.
     */
    private AID winnerAID;
    
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
        return new AuctionResult(winnerDetermined, winnerAID, finalPrice);
    }
    
    // ----- PRIVATE -----
    
    /**
     * Gets my 'Auctioneer' role.
     * @return my 'Auctioneer' role
     */
    private Auctioneer_Role getMyAuctioneer() {
        return (Auctioneer_Role)myAgent;
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
        State successEnd = new SuccessEnd();
        State failureEnd = new FailureEnd();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(sendAuctionCFP);
        registerState(receiveBid);
        
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
     * The 'Send auction CFP' (single sender) state.
     * A state in which the auction call for proposals (CFP) is sent
     * to the bidders.
     */
    private class SendAuctionCFP extends SingleSenderState {

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
     * The 'Receive bid' (single sender) state.
     * A state in which a bid is received from the bidders.
     */
    private class ReceiveBid extends SingleReceiverState {

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
     * The 'Success end' state.
     * A (final) state in which the party successfully ends.
     */
    private class SuccessEnd extends OneShotBehaviourState {

        @Override
        public void action() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * The 'Failure end' state.
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
