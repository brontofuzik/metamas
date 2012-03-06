package thespian4jade.proto;

import thespian4jade.core.organization.Role_InvokeRequirementInitiator;
import thespian4jade.proto.jadeextensions.FSMBehaviourState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;
import java.io.Serializable;

/**
 * A 'Invoke requirement' (party) state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class InvokeRequirementState<TArgument extends Serializable,
    TResult extends Serializable> extends FSMBehaviourState {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String requirementName;
    
    private Role_InvokeRequirementInitiator invokeRequirementInitiator;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected InvokeRequirementState(String requirementName) {        
        this.requirementName = requirementName;
        
        buildFSM();
    }
            
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected abstract TArgument getRequirementArgument();
    
    protected abstract void setRequirementResult(TResult requirementResult);
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----
        State setPowerArgument = new SetRequirementArgument();
        invokeRequirementInitiator = new Role_InvokeRequirementInitiator(requirementName);
        State setResultState = new GetRequirementResult();
        // ------------------
        
        // Register the states.
        registerFirstState(setPowerArgument);
        
        registerState(invokeRequirementInitiator);
        
        registerLastState(setResultState);
        
        // Register the transitions.
        setPowerArgument.registerDefaultTransition(invokeRequirementInitiator);
        
        invokeRequirementInitiator.registerDefaultTransition(setResultState);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Set requirement argument' (one-shot) state.
     */
    private class SetRequirementArgument extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            invokeRequirementInitiator.setRequirementArgument(getRequirementArgument());
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Get requirement result' (one-shot) state.
     */
    private class GetRequirementResult extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setRequirementResult((TResult)invokeRequirementInitiator.getRequirementResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
