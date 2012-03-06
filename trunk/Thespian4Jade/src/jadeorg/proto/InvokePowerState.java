package jadeorg.proto;

import jadeorg.core.player.Player_InvokePowerInitiator;
import jadeorg.proto.jadeextensions.FSMBehaviourState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import java.io.Serializable;

/**
 * An 'Invoke power' (party) state.
 * @author Lukáš Kúdela
 * @since 2012-01-04
 * @version %I% %G%
 */
public abstract class InvokePowerState<TArgument extends Serializable,
    TResult extends Serializable> extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String powerName;
    
    private Player_InvokePowerInitiator invokePowerInitiator;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected InvokePowerState(String powerName) {        
        buildFSM();
    }
            
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract TArgument getPowerArgument();
    
    protected abstract void setPowerResult(TResult powerResult);
    
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
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setPowerResult((TResult)invokePowerInitiator.getPowerResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
