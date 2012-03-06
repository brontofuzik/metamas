package example1.organizations.functioninvocation.invoker;

import jadeorg.core.organization.power.FSMPower;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * The 'Invoke function' (complex) power.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class InvokeFunction_Competence extends FSMPower<Integer, Integer> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private InvokeFunction_InitiatorParty invokeFunctionInitiator;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public InvokeFunction_Competence() {       
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State setPowerArgument = new SetPowerArgument();
        invokeFunctionInitiator = new InvokeFunction_InitiatorParty();
        State getPowerResult = new GetPowerResult(); 
        // ------------------
        
        // Register the states.
        registerFirstState(setPowerArgument);
        
        registerState(invokeFunctionInitiator);
        
        registerLastState(getPowerResult);
        
        // Register the transitions.
        setPowerArgument.registerDefaultTransition(invokeFunctionInitiator);
        
        invokeFunctionInitiator.registerDefaultTransition(getPowerResult);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SetPowerArgument extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            invokeFunctionInitiator.setArgument(getArgument());
        }
        
        // </editor-fold>
    }
    
    private class GetPowerResult extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setResult(invokeFunctionInitiator.getResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
