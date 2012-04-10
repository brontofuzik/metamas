package example2.organizations.expressionevaluation.evaluator;

import thespian4jade.core.organization.competence.SynchronousCompetence;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.special.WrapperState;

/**
 * The 'Evaluate' (synchronous) competence.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Evaluate_Competence extends SynchronousCompetence<String, Integer> {
        
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Evaluate_Competence class.
     */
    public Evaluate_Competence() {
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the competence FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState evaluateExpressionWrapper = new EvaluateExpressionWrapper();
        IState end = new End();
        // ------------------
        
        // Register the states.
        registerFirstState(evaluateExpressionWrapper);
        registerLastState(end);
        
        // Register the transitions.
        evaluateExpressionWrapper.registerDefaultTransition(end);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Evaluate expression' initial (wrapper) state.
     * A state in which the 'Evaluate expression' behaviour is executed.
     */
    private class EvaluateExpressionWrapper
        extends WrapperState<EvaluateExpression> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initialize a nex instance of the EvaluateExpressionWrapper class.
         */
        public EvaluateExpressionWrapper() {
            super(new EvaluateExpression());
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void doActionBefore(EvaluateExpression wrappedState) {
            wrappedState.setExpression(getArgument());
        }

        @Override
        protected void doActionAfter(EvaluateExpression wrappedState) {
            setResult(new Integer(wrappedState.getValue()));
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
