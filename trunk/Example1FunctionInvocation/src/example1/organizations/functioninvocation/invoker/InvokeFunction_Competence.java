package example1.organizations.functioninvocation.invoker;

import thespian4jade.core.organization.competence.SynchronousCompetence;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.StateWrapperState;

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
     * The 'Invoke function initiator party' (state wrapper) state. 
     */
    private class InvokeFunctionInitiatorWrapper
        extends StateWrapperState<InvokeFunction_InitiatorParty> {

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
        protected void setWrappedStateArgument(InvokeFunction_InitiatorParty wrappedState) {
            wrappedState.setArgument(getArgument());
        }

        @Override
        protected void getWrappedStateResult(InvokeFunction_InitiatorParty wrappedState) {
            setResult(wrappedState.getResult());
        }
    
        // </editor-fold>    
    }
    
    /**
     * The 'End' (one-shot) state.
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
