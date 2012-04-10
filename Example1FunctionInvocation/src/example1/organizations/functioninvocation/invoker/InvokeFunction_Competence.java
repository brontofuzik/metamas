package example1.organizations.functioninvocation.invoker;

import thespian4jade.core.organization.competence.SynchronousCompetence;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.special.WrapperState;

/**
 * The 'Invoke function' (synchronous) competence.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class InvokeFunction_Competence extends SynchronousCompetence<Integer, Integer> {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the InvokeFunction_Competence class.
     */
    public InvokeFunction_Competence() {       
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the competence FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState invokeFunctionInitiatorWrapper = new InvokeFunctionInitiatorWrapper();
        IState end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(invokeFunctionInitiatorWrapper);       
        registerLastState(end);
        
        // Register the transitions.
        invokeFunctionInitiatorWrapper.registerDefaultTransition(end);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Invoke function initiator party' initial (wrapper) state.
     * A state in which the 'Invoke function' protocol initiator party is executed.
     */
    private class InvokeFunctionInitiatorWrapper
        extends WrapperState<InvokeFunction_InitiatorParty> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the InvokeFunctionInitiatorWrapper class.
         */
        InvokeFunctionInitiatorWrapper() {
            super(new InvokeFunction_InitiatorParty());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void doActionBefore(InvokeFunction_InitiatorParty wrappedState) {
            wrappedState.setArgument(getArgument());
        }

        @Override
        protected void doActionAfter(InvokeFunction_InitiatorParty wrappedState) {
            setResult(wrappedState.getResult());
        }
    
        // </editor-fold>    
    }
    
    /**
     * The 'End' final (one-shot) state.
     */
    private class End extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
