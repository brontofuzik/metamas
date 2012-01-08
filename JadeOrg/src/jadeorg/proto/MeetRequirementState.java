package jadeorg.proto;

import jadeorg.core.organization.Role_MeetRequirementInitiator;
import jadeorg.proto.jadeextensions.FSMBehaviourState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;

/**
 * A 'Meet requirement' (party) state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class MeetRequirementState extends FSMBehaviourState {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String requirementName;
    
    private Role_MeetRequirementInitiator meetRequirementInitiator;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected MeetRequirementState(String name, String requirementName) {
        super(name);
        
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
        meetRequirementInitiator = new Role_MeetRequirementInitiator(requirementName);
        State setResultState = new GetRequirementResult();
        // ------------------
        
        // Register the states.
        registerFirstState(setPowerArgument);
        
        registerState(meetRequirementInitiator);
        
        registerLastState(setResultState);
        
        // Register the transitions.
        setPowerArgument.registerDefaultTransition(meetRequirementInitiator);
        
        meetRequirementInitiator.registerDefaultTransition(setResultState);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Set requirement argument' (one-shot) state.
     */
    private class SetRequirementArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "set-requirement-argument";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        SetRequirementArgument() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            meetRequirementInitiator.setRequirementArgument(getRequirementArgument());
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Get requirement result' (one-shot) state.
     */
    private class GetRequirementResult extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "get-requirement-result";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        GetRequirementResult() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            setRequirementResult(meetRequirementInitiator.getRequirementResult());
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
