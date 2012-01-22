package example2.organizations.auction.auctioneer;

import jadeorg.core.organization.power.FSMPower;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * The 'Auction' power.
 * @author Lukáš Kúdela
 * @since 2012-01-18
 * @version %I% %G%
 */
public class Auction_Power extends FSMPower<AuctionArgument, AuctionResult> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AuctionType auctionType;
    
    private AuctionInitiator auctionInitiator;
    
    //private Auctioneer_EnglishAuctionInitiator englishAuctionInitiator;
    
    //private Auctioneer_DutchAuctionInitiator dutchAuctionInitiator;
    
    //private Auctioneer_EnvelopeAuctionInitiator envelopeAuctionInitiator;
    
    //private Auctioneer_VickereyAuctionInitiator vickreyAuctionInitiator;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Auction_Power() {       
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State initialize = new Initialize();
        State setPowerArgument = new SetPowerArgument();
        State selectAuctionType = new SelectAuctionType();
        Auctioneer_EnglishAuctionInitiator englishAuctionInitiator =
            new Auctioneer_EnglishAuctionInitiator();
        Auctioneer_DutchAuctionInitiator dutchAuctionInitiator =
            new Auctioneer_DutchAuctionInitiator();
        Auctioneer_EnvelopeAuctionInitiator envelopeAuctionInitiator =
            new Auctioneer_EnvelopeAuctionInitiator();
        Auctioneer_VickereyAuctionInitiator vickreyAuctionInitiator =
            new Auctioneer_VickereyAuctionInitiator();
        State getPowerResult = new GetPowerResult(); 
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(setPowerArgument);
        registerState(selectAuctionType);
        registerState(englishAuctionInitiator);
        registerState(dutchAuctionInitiator);
        registerState(envelopeAuctionInitiator);
        registerState(vickreyAuctionInitiator);
        
        registerLastState(getPowerResult);
        
        // Register the transitions.
        // The 'Initialize' state
        initialize.registerDefaultTransition(setPowerArgument);
        
        // The 'Set power argument' state
        setPowerArgument.registerDefaultTransition(selectAuctionType);
        
        // The 'Select auction type' state
        selectAuctionType.registerTransition(englishAuctionInitiator
            .getAuctionType().getValue(), englishAuctionInitiator);
        selectAuctionType.registerTransition(dutchAuctionInitiator
            .getAuctionType().getValue(), dutchAuctionInitiator);
        selectAuctionType.registerTransition(envelopeAuctionInitiator
            .getAuctionType().getValue(), envelopeAuctionInitiator);
        selectAuctionType.registerTransition(vickreyAuctionInitiator
            .getAuctionType().getValue(), vickreyAuctionInitiator);
        
        // The 'English auction initiator' state
        englishAuctionInitiator.registerDefaultTransition(getPowerResult);
        
        // The 'Dutch auction initiator' state
        dutchAuctionInitiator.registerDefaultTransition(getPowerResult);
        
        // The 'Envelope auction initiator' state
        envelopeAuctionInitiator.registerDefaultTransition(getPowerResult);
        
        // The 'Vickrey auction initiator' state
        vickreyAuctionInitiator.registerDefaultTransition(getPowerResult);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class Initialize extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            auctionType = getArgument().getAuctionType();
        }
        
        // </editor-fold>
    }
    
    private class SetPowerArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            auctionInitiator.setAuctionArgument(getArgument());
        }
        
        // </editor-fold>
    }
    
    private class SelectAuctionType extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }
        
        @Override
        public int onEnd() {
            return auctionInitiator.getAuctionType().getValue();
        }
        
        // </editor-fold>
    }
    
    private class GetPowerResult extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setResult(auctionInitiator.getAuctionResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
