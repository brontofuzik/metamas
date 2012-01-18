package example1.organizations.powers;

import example1.organizations.Asker_CalculateFactorialInitiator;
import jadeorg.core.organization.power.FSMPower;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * The 'Calculate factorial' (complex) power.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class CalculateFactorial_Power extends FSMPower<Integer, Integer> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Asker_CalculateFactorialInitiator calculateFactorialInitiator;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public CalculateFactorial_Power() {       
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State setPowerArgument = new SetPowerArgument();
        calculateFactorialInitiator = new Asker_CalculateFactorialInitiator();
        State getPowerResult = new GetPowerResult(); 
        // ------------------
        
        // Register the states.
        registerFirstState(setPowerArgument);
        
        registerState(calculateFactorialInitiator);
        
        registerLastState(getPowerResult);
        
        // Register the transitions.
        setPowerArgument.registerDefaultTransition(calculateFactorialInitiator);
        
        calculateFactorialInitiator.registerDefaultTransition(getPowerResult);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SetPowerArgument extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            calculateFactorialInitiator.setArgument(getArgument());
        }
        
        // </editor-fold>
    }
    
    private class GetPowerResult extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setResult(calculateFactorialInitiator.getResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
