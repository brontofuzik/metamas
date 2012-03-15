package example1.organizations.functioninvocation.invoker;

import thespian4jade.core.organization.power.FSMPower;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

/**
 * The 'Invoke function' (FSM) power.
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
        State setInitiatorArgument = new SetInitiatorArgument();
        invokeFunctionInitiator = new InvokeFunction_InitiatorParty();
        State getInitiatorResult = new GetInitiatorResult(); 
        // ------------------
        
        // Register the states.
        registerFirstState(setInitiatorArgument);
        
        registerState(invokeFunctionInitiator);
        
        registerLastState(getInitiatorResult);
        
        // Register the transitions.
        setInitiatorArgument.registerDefaultTransition(invokeFunctionInitiator);
        
        invokeFunctionInitiator.registerDefaultTransition(getInitiatorResult);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class SetInitiatorArgument extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            invokeFunctionInitiator.setArgument(getArgument());
        }
        
        // </editor-fold>
    }
    
    private class GetInitiatorResult extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setResult(invokeFunctionInitiator.getResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
