package jadeorg.proto;

import jadeorg.core.organization.Role_InvokeRequirementInitiator;
import jadeorg.proto.jadeextensions.FSMBehaviourState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * A 'Invoke requirement' (party) state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class InvokeRequirementState extends FSMBehaviourState {

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
    
    protected abstract Object getRequirementArgument();
    
    protected abstract void setRequirementResult(Object requirementResult);
    
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
            setRequirementResult(invokeRequirementInitiator.getRequirementResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
