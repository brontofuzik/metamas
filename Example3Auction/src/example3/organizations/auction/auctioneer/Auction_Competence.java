package example3.organizations.auction.auctioneer;

import example3.organizations.auction.auctioneer.auction.AuctionArgument;
import example3.organizations.auction.auctioneer.auction.AuctionResult;
import example3.organizations.auction.auctioneer.auction.AuctionType;
import thespian4jade.core.organization.competence.SynchronousCompetence;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.special.WrapperState;

/**
 * The 'Auction' (synchronous) competence.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Auction_Competence extends SynchronousCompetence<AuctionArgument, AuctionResult> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The 'Initialize' initial state.
     */
    private IState initialize;
    
    /**
     * The 'End' final state.
     */
    private IState end;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Auction_Competence class.
     */
    public Auction_Competence() {       
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the competence FSM.
     */
    private void buildFSM() {
        // ----- States -----
        initialize = new Initialize();
        end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(initialize); 
        registerLastState(end);
    }
    
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
            AuctionType auctionType = getArgument().getAuctionType();
            Auction_InitiatorParty auctionInitiator = Auction_InitiatorParty.createAuctionInitiator(auctionType);
            AuctionInitiatorWrapper auctionInitiatorWrapper = new AuctionInitiatorWrapper(auctionInitiator);
            
            // Register the auction initiator related states.
            registerState(auctionInitiatorWrapper);
            
            // Register the auction initiator related transitions.
            initialize.registerDefaultTransition(auctionInitiatorWrapper);
            auctionInitiatorWrapper.registerDefaultTransition(end);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Auction initiator party' (wrapper) state.
     * A state in which the 'Auction' protocol initiator party is executed.
     */
    private class AuctionInitiatorWrapper
        extends WrapperState<Auction_InitiatorParty> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initliazes a new instance of the AuctionInitiatorWrapper class.
         * @param auctionInitiator the auction initiator party
         */
        AuctionInitiatorWrapper(Auction_InitiatorParty auctionInitiator) {
            super(auctionInitiator);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void doActionBefore(Auction_InitiatorParty wrappedState) {
            wrappedState.setAuctionArgument(getArgument());
        }

        @Override
        protected void doActionAfter(Auction_InitiatorParty wrappedState) {
            setResult(wrappedState.getAuctionResult());
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'End' final (one-shot) state.
     * A state in which the party ends.
     */
    private static class End extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
