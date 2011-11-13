package jadeorg.core.organization;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;
import jadeorg.proto.ActiveState;
import jadeorg.proto.State;
import java.util.LinkedList;
import java.util.List;

/**
 * A power (FSM) behaviour.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public class Power extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AID playerAID;
    
    private String arguments;
    
    private Object result;
    
    private List<State> states = new LinkedList<State>(); 
    
    private State initialState;
    
    private State finalState;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Power(String name) {
        // ----- Preconditions -----
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name");
        }
        
        setBehaviourName(name);        
        initializeFSM();
    }
    
    private void initializeFSM() {
        // ----- States -----
        finalState = new End();
        // ------------------
        
        // Register states.
        registerLastState(finalState, finalState.getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return getBehaviourName();
    }
    
    public Role getMyRole() {
        return (Role)myAgent;
    }
    
    public AID getPlayerAID() {
        return playerAID;
    }
    
    public void setPlayerAID(AID playerAID) {
        // ----- Preconditions -----
        assert playerAID != null;
        
        this.playerAID = playerAID;
    }
    
    public void setArguments(String arguments) {
        // ----- Preconditions -----
        assert arguments != null && !arguments.isEmpty();
        
        this.arguments = arguments;
    }
    
    public Object getResult() {
        return result;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addState(State state) {
        if (states.isEmpty()) {
            initialState = state;
        }
        states.add(state);
    }
    
    public void buildFSM() {
        while (!states.isEmpty()) {
            State state = states.remove(0);     
            if (state == initialState) {
                registerFirstState(state, state.getName());
            } else {
                registerState(state, state.getName());
            }
            // state ---[FAILURE]---> finalState
            registerTransition(state.getName(), finalState.getName(), State.Event.FAILURE.getCode());
            // state ---[FAILURE_LOOP]---> state
            registerTransition(state.getName(), state.getName(), State.Event.FAILURE_LOOP.getCode());

            if (!states.isEmpty()) {
                // There is a next state.
                State nextState = states.get(0);
                
                // REQUIREMENT
                String requirement = state.getRequirement();                    
                if (requirement != null) {
                    // There is a requirement.
                    MeetRequirementInitiator requestRequirementState = new MeetRequirementInitiator(requirement);  
                    
                    // Register the state.
                    registerState(requestRequirementState, requestRequirementState.getName());
                    
                    // Register the transitions.
                    // state ---[SUCCESS]---> requestRequirementState
                    registerTransition(state.getName(), requestRequirementState.getName(), State.Event.SUCCESS.getCode());
                    // requestRequirementState ---[SUCCES]---> nextState
                    registerTransition(requestRequirementState.getName(), nextState.getName(), State.Event.SUCCESS.getCode());                 
                    
                    // EXCEPTION
                    String exception = state.getException();
                    if (exception != null) {
                        ErrorHandler errorHandler = new ErrorHandler(requirement + "error-handler");
                        
                        // Register the state.     
                        registerState(errorHandler, errorHandler.getName());
                        
                        // Register the transitions.
                        // requestRequirementState ---[FAILURE]---> errorHandler
                        registerTransition(requestRequirementState.getName(), errorHandler.getName(), State.Event.FAILURE.getCode());
                        // errorHandler ---[SUCCESS]---> nextState
                        registerTransition(errorHandler.getName(), nextState.getBehaviourName(), State.Event.SUCCESS.getCode());
                        // errorHandler ---[FAILURE]---> finalState
                        registerTransition(errorHandler.getName(), finalState.getName(), State.Event.FAILURE.getCode());  
                    }
                } else {
                    // There is no requirement.
                    registerTransition(state.getName(), nextState.getName(), State.Event.SUCCESS.getCode());    
                }
            } else {
                // There is no next state. Therefore, this is the last state before the final state.
                registerTransition(state.getName(), finalState.getName(), State.Event.SUCCESS.getCode());
            }
        } // while (!states.isEmpty())
    }
    
    @Override
    public int onEnd() {
        reset();
        return 0;
    }
    
    public void setRequirementArgument(String requirementName, Object argument) {
        MeetRequirementInitiator requirementState = (MeetRequirementInitiator)getState(requirementName);
        requirementState.setArgument(argument);
    }
    
    public Object getRequirementResult(String requirementName) {
        MeetRequirementInitiator requirementState = (MeetRequirementInitiator)getState(requirementName);
        return requirementState.getResult();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class End extends ActiveState {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "end";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        End() {
            super(NAME);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>


}
