package example2.organizations.auction.auctioneer;

import example2.organizations.auction.auctioneer.Auction_InitiatorParty;
import example2.organizations.auction.auctioneer.auction.AuctionArgument;
import example2.organizations.auction.auctioneer.auction.AuctionResult;
import example2.organizations.auction.auctioneer.auction.AuctionType;
import thespian4jade.core.organization.power.FSMPower;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

/**
 * The 'Auction' competence.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Auction_Competence extends FSMPower<AuctionArgument, AuctionResult> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The 'Set competence argument' state.
     */
    private State setCompetenceArgument;
    
    /**
     * The 'Auction initiator' party/state.
     */
    private Auction_InitiatorParty auctionInitiator;
    
    /**
     * The 'Get competence result' state.
     */
    private State getCompetenceResult;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Auction_Competence() {       
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State initialize = new Initialize();
        setCompetenceArgument = new SetCompetenceArgument();
//        State selectAuctionType = new SelectAuctionType();
//        Auctioneer_EnglishAuctionInitiator englishAuctionInitiator =
//            new Auctioneer_EnglishAuctionInitiator();
//        Auctioneer_DutchAuctionInitiator dutchAuctionInitiator =
//            new Auctioneer_DutchAuctionInitiator();
//        Auctioneer_EnvelopeAuctionInitiator envelopeAuctionInitiator =
//            new Auctioneer_EnvelopeAuctionInitiator();
//        Auctioneer_VickereyAuctionInitiator vickreyAuctionInitiator =
//            new Auctioneer_VickereyAuctionInitiator();
        getCompetenceResult = new GetCompetenceResult(); 
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(setCompetenceArgument);
//        registerState(selectAuctionType);
//        registerState(englishAuctionInitiator);
//        registerState(dutchAuctionInitiator);
//        registerState(envelopeAuctionInitiator);
//        registerState(vickreyAuctionInitiator);
        
        registerLastState(getCompetenceResult);
        
        // Register the transitions.
        // The 'Initialize' state
        initialize.registerDefaultTransition(setCompetenceArgument);
        
//        // The 'Set competence argument' state
//        setCompetenceArgument.registerDefaultTransition(selectAuctionType);
//        
//        // The 'Select auction type' state
//        selectAuctionType.registerTransition(englishAuctionInitiator
//            .getAuctionType().getValue(), englishAuctionInitiator);
//        selectAuctionType.registerTransition(dutchAuctionInitiator
//            .getAuctionType().getValue(), dutchAuctionInitiator);
//        selectAuctionType.registerTransition(envelopeAuctionInitiator
//            .getAuctionType().getValue(), envelopeAuctionInitiator);
//        selectAuctionType.registerTransition(vickreyAuctionInitiator
//            .getAuctionType().getValue(), vickreyAuctionInitiator);
//        
//        // The 'English auction initiator' state
//        englishAuctionInitiator.registerDefaultTransition(getCompetenceResult);
//        
//        // The 'Dutch auction initiator' state
//        dutchAuctionInitiator.registerDefaultTransition(getCompetenceResult);
//        
//        // The 'Envelope auction initiator' state
//        envelopeAuctionInitiator.registerDefaultTransition(getCompetenceResult);
//        
//        // The 'Vickrey auction initiator' state
//        vickreyAuctionInitiator.registerDefaultTransition(getCompetenceResult);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            AuctionType auctionType = getArgument().getAuctionType();
            auctionInitiator = Auction_InitiatorParty.createAuctionInitiator(auctionType);
            
            // Register the auction initiator related states.
            registerState(auctionInitiator);
            
            // Register the auction initiator related transitions.
            setCompetenceArgument.registerDefaultTransition(auctionInitiator);
            
            auctionInitiator.registerDefaultTransition(getCompetenceResult);
        }
        
        // </editor-fold>
    }
    
    private class SetCompetenceArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            auctionInitiator.setAuctionArgument(getArgument());
        }
        
        // </editor-fold>
    }
    
    private class GetCompetenceResult extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setResult(auctionInitiator.getAuctionResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
