package jadeorg.proto;

import jadeorg.core.player.Player_InvokePowerInitiator;
import jadeorg.proto.jadeextensions.FSMBehaviourState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * An 'Invoke power' (party) state.
 * @author Lukáš Kúdela
 * @since 2012-01-04
 * @version %I% %G%
 */
public abstract class InvokePowerState extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String powerName;
    
    private Player_InvokePowerInitiator invokePowerInitiator;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected InvokePowerState(String name, String powerName) {
        super(name);
        
        buildFSM();
    }
            
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract Object getPowerArgument();
    
    protected abstract void setPowerResult(Object powerResult);
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State setPowerArgument = new SetPowerArgument();
        invokePowerInitiator = new Player_InvokePowerInitiator(powerName);
        State setResultState = new GetPowerResult();
        // ------------------
        
        // Register the states.
        registerFirstState(setPowerArgument);
        
        registerState(invokePowerInitiator);
        
        registerLastState(setResultState);
        
        // Register the transitions.
        setPowerArgument.registerDefaultTransition(invokePowerInitiator);
        
        invokePowerInitiator.registerDefaultTransition(setResultState);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Set power argument' (one-shot) state.
     */
    private class SetPowerArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "set-power-argument";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SetPowerArgument() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            invokePowerInitiator.setPowerArgument(getPowerArgument());
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Get power result' (one-shot) state.
     */
    private class GetPowerResult extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "get-power-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        GetPowerResult() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setPowerResult(invokePowerInitiator.getPowerResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
