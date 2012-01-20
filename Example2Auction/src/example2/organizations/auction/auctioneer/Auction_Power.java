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
    
    private Auctioneer_EnglishAuctionInitiator englishAuctionInitiator;
    
    private Auctioneer_DutchAuctionInitiator dutchAuctionInitiator;
    
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
        englishAuctionInitiator = new Auctioneer_EnglishAuctionInitiator();
        dutchAuctionInitiator = new Auctioneer_DutchAuctionInitiator();
        State getPowerResult = new GetPowerResult(); 
        // ------------------
        
        // Register the states.
        registerFirstState(initialize);
        
        registerState(setPowerArgument);
        registerState(selectAuctionType);
        registerState(englishAuctionInitiator);
        registerState(dutchAuctionInitiator);
        
        registerLastState(getPowerResult);
        
        // Register the transitions.
        initialize.registerDefaultTransition(setPowerArgument);
        
        setPowerArgument.registerDefaultTransition(selectAuctionType);
        
        selectAuctionType.registerTransition(englishAuctionInitiator.getName().hashCode(),
            englishAuctionInitiator);
        selectAuctionType.registerTransition(dutchAuctionInitiator.getName().hashCode(),
            dutchAuctionInitiator);
        
        englishAuctionInitiator.registerDefaultTransition(getPowerResult);
        
        dutchAuctionInitiator.registerDefaultTransition(getPowerResult);
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
            switch (auctionType) {
                case ENGLISH:
                    englishAuctionInitiator.setAuctionArgument(getArgument());
                    
                case DUTCH:
                    dutchAuctionInitiator.setAuctionArgument(getArgument());
                    
                default:
                    englishAuctionInitiator.setAuctionArgument(getArgument());                               
            }
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
            switch (auctionType) {
                case ENGLISH:
                    return englishAuctionInitiator.getCode();
                    
                case DUTCH:
                    return dutchAuctionInitiator.getCode();
                    
                default:
                    return englishAuctionInitiator.getCode();                               
            }
        }
        
        // </editor-fold>
    }
    
    private class GetPowerResult extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            switch (auctionType) {
                case ENGLISH:
                    setResult(englishAuctionInitiator.getAuctionResult());
                    
                case DUTCH:
                    setResult(dutchAuctionInitiator.getAuctionResult());
                    
                default:
                    setResult(englishAuctionInitiator.getAuctionResult());                              
            }
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
