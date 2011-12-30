package jadeorg.core.organization;

import jadeorg.proto.jadeextensions.FSMBehaviourState;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;
import jadeorg.proto.jadeextensions.State;
import java.util.LinkedList;
import java.util.List;

/**
 * A power (FSM) state.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public class Power extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    //private AID playerAID;
    
    private Object argument;
    
    private Object result;
    
    private List<State> states = new LinkedList<State>(); 
    
    private State initialState;
    
    private State finalState;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Power(String name) {
        super(name);
        // ----- Preconditions -----
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name");
        }
        // -------------------------
               
        registerStatesAndTransitions();
    }
    
    private void registerStatesAndTransitions() {
        // ----- States -----
        finalState = new End();
        // ------------------
        
        // Register the states.
        registerLastState(finalState);
        
        // register the transitions.
        // No transitions.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
//    public AID getPlayerAID() {
//        return playerAID;
//    }
//    
//    public void setPlayerAID(AID playerAID) {
//        // ----- Preconditions -----
//        assert playerAID != null;
//        // -------------------------
//        
//        this.playerAID = playerAID;
//    }
    
    Object getArgument() {
        return argument;
    }
    
    Power setArgument(Object argument) {        
        this.argument = argument;
        return this;
    }
    
    Object getResult() {
        return result;
    }
    
    Power setResult(Object result) {
        this.result = result;
        return this;
    }
    
    // ----- PROTECTED -----
    
    protected Role getMyRole() {
        return (Role)myAgent;
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
//        
//        final int SUCCESS = 1;
//        final int FAILURE = 2;
//        
//        while (!states.isEmpty()) {
//            State state = states.remove(0);     
//            if (state == initialState) {
//                registerFirstState(state);
//            } else {
//                registerState(state);
//            }
//            
//            state.registerTransition(FAILURE, finalState);
//
//            if (!states.isEmpty()) {
//                // There is a next state.
//                State nextState = states.get(0);
//                
//                // REQUIREMENT
//                String requirement = state.getRequirement();                    
//                if (requirement != null) {
//                    // There is a requirement.
//                    Role_MeetRequirementInitiator meetRequirementInitiator = new Role_MeetRequirementInitiator(requirement);  
//                    
//                    // Register the state.
//                    registerState(meetRequirementInitiator);
//                    
//                    // Register the transitions.
//                    state.registerTransition(SUCCESS, meetRequirementInitiator);
//                    meetRequirementInitiator.registerTransition(SUCCESS, nextState);                 
//                    
//                    // EXCEPTION
//                    String exception = state.getException();
//                    if (exception != null) {
//                        ErrorHandler errorHandler = new ErrorHandler(requirement + "error-handler");
//                        
//                        // Register the state.     
//                        registerState(errorHandler);
//                        
//                        // Register the transitions.
//                        meetRequirementInitiator.registerTransition(FAILURE, errorHandler.getName());
//                        // errorHandler ---[SUCCESS]---> nextState
//                        errorHandler.registerTransition(SUCCESS, nextState);
//                        // errorHandler ---[FAILURE]---> finalState
//                        errorHandler.registerTransition(FAILURE, finalState);  
//                    }
//                } else {
//                    // There is no requirement.
//                    state.registerTransition(SUCCESS, nextState);    
//                }
//            } else {
//                // There is no next state. Therefore, this is the last state before the final state.
//                state.registerTransition(SUCCESS, finalState);
//            }
//        } // while (!states.isEmpty())
    }
    
    @Override
    public int onEnd() {
        reset();
        return 0;
    }
    
    public void setRequirementArgument(String requirementName, Object argument) {
        Role_MeetRequirementInitiator requirementState = (Role_MeetRequirementInitiator)getState(requirementName);
        requirementState.setArgument(argument);
    }
    
    public Object getRequirementResult(String requirementName) {
        Role_MeetRequirementInitiator requirementState = (Role_MeetRequirementInitiator)getState(requirementName);
        return requirementState.getResult();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class End extends OneShotBehaviourState {

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
