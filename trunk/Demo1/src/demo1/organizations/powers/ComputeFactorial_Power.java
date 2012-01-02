package demo1.organizations.powers;

import jadeorg.core.organization.Power;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * The 'Compute factorial' power.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class ComputeFactorial_Power extends Power {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "compute-factorial";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ComputeFactorial_Power() {
        super(NAME);
        
        registerStatesAndTransitions();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        State state1 = new State1();
        // ------------------
        
        // Register the states.
        registerFirstState(state1);
        registerLastState(state1);
        
        // Register the transitions.
        // No transitions.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class State1 extends OneShotBehaviourState {

        private static final String NAME = "state1";
        
        State1() {
            super(NAME);
        }
        
        @Override
        public void action() {
            Object argument = (Integer)getArgument();
            setResult(argument);
        }
    
    }
    
    // </editor-fold>
}
