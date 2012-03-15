package example3.organizations.expressionevaluation.evaluator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import thespian4jade.proto.jadeextensions.FSMBehaviourState;
import thespian4jade.proto.jadeextensions.OneShotBehaviourState;
import thespian4jade.proto.jadeextensions.State;

/**
 * The 'Evaluate expression' (FSM) behaviour.
 * @author Lukáš Kúdela
 * @since 2012-04-15
 * @version %I% %G%
 */
public class EvaluateExpression extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    // ----- States -----
    private State setInitiatorArgument;
    private EvaluateBinaryOperation_InitiatorParty evaluateBinaryOperationInitiator;
    private State getInitiatorResult;
    // ------------------
    
    private String expression;
    
    private String operand1;
    
    private String operand2;
    
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the EvaluateExpression class.
     */
    public EvaluateExpression() {        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    void setExpression(String expression) {
        this.expression = expression;
    }
    
    int getValue() {
        return value;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void buildFSM() {
        // ----- States -----     
        State process = new Process();
        setInitiatorArgument = new SetInitiatorArgument();
        getInitiatorResult = new GetInitiatorResult();
        State end = new End();
        // ------------------
        
        // Register the states.        
        registerFirstState(process);      
        registerState(setInitiatorArgument);
        registerState(getInitiatorResult);     
        registerLastState(end);
        
        // Register the transitions.        
        process.registerTransition(Process.NUMBER, end);
        process.registerTransition(Process.BINARY_OPERATION, setInitiatorArgument);      
        getInitiatorResult.registerDefaultTransition(end);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Process' (one-shot) state.
     */
    private class Process extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        // ----- Exit values -----
        static final int NUMBER = 1;
        static final int UNARY_OPERATION = 2;
        static final int BINARY_OPERATION = 3;
        // -----------------------
        
        private int exitValue;
        
        // </editor-fold>
        
        @Override
        public void action() {
            // TODO Try to change the operator order.
            final Pattern pattern = Pattern.compile("\\(([^)]*)\\)([*+-/])\\(([^)]*)\\)");
            Matcher matcher = pattern.matcher(expression);
            boolean matches = matcher.matches();
            
            if (matches) {
                // Binary operation
                operand1 = matcher.group(1);
                operand2 = matcher.group(3);
                switch (matcher.group(2).charAt(0)) {
                    case '+':
                        evaluateBinaryOperationInitiator = new EvaluateAddition_InitiatorParty();
                        break;
                        
                    case '-':
                        evaluateBinaryOperationInitiator = new EvaluateSubtraction_InitiatorParty();
                        break;
                        
                    case '*':
                        evaluateBinaryOperationInitiator = new EvaluateMultiplication_InitiatorParty();
                        break;
                        
                    case '/':
                        evaluateBinaryOperationInitiator = new EvaluateDivision_InitiatorParty();
                        break;
                }
                registerState(evaluateBinaryOperationInitiator);
                setInitiatorArgument.registerDefaultTransition(evaluateBinaryOperationInitiator);
                evaluateBinaryOperationInitiator.registerDefaultTransition(getInitiatorResult);
                exitValue = BINARY_OPERATION;
            } else {
                // Number
                value = new Integer(expression).intValue();
                exitValue = NUMBER;
            }
        }

        @Override
        public int onEnd() {
            return exitValue;
        }    
    }
    
    /**
     * The 'Set initiator argument' (one-shot) state.
     */
    private class SetInitiatorArgument extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            evaluateBinaryOperationInitiator.setOperand1(operand1);
            evaluateBinaryOperationInitiator.setOperand2(operand2);
        }
    
        // </editor-fold>
    }
    
    /**
     * The 'Get initiator result' (one-shot) state.
     */
    private class GetInitiatorResult extends OneShotBehaviourState {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            value = evaluateBinaryOperationInitiator.getResult();
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
