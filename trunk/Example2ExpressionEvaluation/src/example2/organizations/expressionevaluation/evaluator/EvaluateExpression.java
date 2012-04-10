package example2.organizations.expressionevaluation.evaluator;

import thespian4jade.behaviours.states.FSMBehaviourState;
import thespian4jade.behaviours.states.OneShotBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.special.WrapperState;

/**
 * The 'EvaluateExpression' (FSM) behaviour.
 * @author Lukáš Kúdela
 * @since 2012-04-15
 * @version %I% %G%
 */
public class EvaluateExpression extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    // ----- States -----
    private IState initialize;
    private IState end;
    // ------------------
    
    /**
     * The expression to evaluate.
     */
    private String expression;
    
    /**
     * The first operand expression.
     */
    private String operand1;
    
    /**
     * The second operand expression.
     */
    private String operand2;
    
    /**
     * The expression value.
     */
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
    
    /**
     * Sets the expression to evalaute.
     * @param expression the expression to evaluate
     */
    void setExpression(String expression) {
        this.expression = expression;
    }
    
    /**
     * Gets the expression value.
     * @return the expression value
     */
    int getValue() {
        return value;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Builds the behaviour FSM. 
     */
    private void buildFSM() {
        // ----- States -----     
        initialize = new Initialize();
        end = new End();
        // ------------------
        
        // Register the states.        
        registerFirstState(initialize);   
        registerLastState(end);
        
        // Register the transitions.        
        initialize.registerTransition(Initialize.NUMBER, end);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Initialize' initial (one-shot) state.
     */
    private class Initialize extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        // ----- Exit values -----
        static final int NUMBER = 1;
        static final int UNARY_OPERATION = 2;
        static final int BINARY_OPERATION = 3;
        // -----------------------
        
        private int exitValue;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            ExpressionParser parser = new ExpressionParser();
            parser.parse(expression);
            if (parser.getOperation() != Operation.NONE) {
                // Binary operation
                operand1 = parser.getOperand1();
                operand2 = parser.getOperand2();
                
                EvaluateBinaryOperation_InitiatorParty evaluateBinaryOperationInitiator
                    = new EvaluateBinaryOperation_InitiatorParty(parser.getOperation());
                EvaluateBinaryOperationInitiatorWrapper evaluateBinaryOperationInitiatorWrapper
                    = new EvaluateBinaryOperationInitiatorWrapper(evaluateBinaryOperationInitiator);
                
                // Register the state.
                registerState(evaluateBinaryOperationInitiatorWrapper);
                
                // Register the transitions.
                initialize.registerTransition(Initialize.BINARY_OPERATION, evaluateBinaryOperationInitiatorWrapper);
                evaluateBinaryOperationInitiatorWrapper.registerDefaultTransition(end);
                
                exitValue = BINARY_OPERATION;
            } else {
                // Number
                value = parser.getNumber();             
                exitValue = NUMBER;
            }
        }

        @Override
        public int onEnd() {
            return exitValue;
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Evaluate binary operation initiator party' initial (wrapper) state.
     * A state in which the 'Evaluate binary operation' protocol initiator party is executed.
     */
    private class EvaluateBinaryOperationInitiatorWrapper
        extends WrapperState<EvaluateBinaryOperation_InitiatorParty> {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        EvaluateBinaryOperationInitiatorWrapper(EvaluateBinaryOperation_InitiatorParty evaluateBinaryInitiator) {
            super(evaluateBinaryInitiator);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void doActionBefore(EvaluateBinaryOperation_InitiatorParty wrappedState) {
            wrappedState.setOperand1(operand1);
            wrappedState.setOperand2(operand2);
        }

        @Override
        protected void doActionAfter(EvaluateBinaryOperation_InitiatorParty wrappedState) {
            value = wrappedState.getResult();
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
    
    /**
     * An expression parser.
     */
    private class ExpressionParser {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /**
         * The operation.
         */
        private Operation operation;
        
        /**
         * The first operand.
         */
        private String operand1;
        
        /**
         * The second operand.
         */
        private String operand2;
        
        /**
         * The number.
         */
        private int number;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        void parse(String expression) {
            // Initialization
            int additionOperatorPosition = -1;
            int multiplicationOperatorPosition = -1;
            int parentheses = 0;
            
            for (int i = 0; i < expression.length(); i++) {
                switch (expression.charAt(i)) {
                    case '(':
                        parentheses++;
                        break;
                    case ')':
                        parentheses--;
                        break;
                    case '+':
                    case '-':
                        if (parentheses == 0) {
                            additionOperatorPosition = i;
                        }
                        break;
                    case '*':
                    case '/':
                        if (parentheses == 0) {
                            multiplicationOperatorPosition = i;
                        }
                        break;
                }
            }
            
            if (additionOperatorPosition > -1 || multiplicationOperatorPosition > -1) {
                // Binary operation
                int operatorPosition = (additionOperatorPosition > -1) ?
                    additionOperatorPosition :
                    multiplicationOperatorPosition;
                switch (expression.charAt(operatorPosition)) {
                    case '+':
                        operation = Operation.ADDITION;
                        break;
                    case '-':
                        operation = Operation.SUBTRACTION;
                        break;
                    case '*':
                        operation = Operation.MULTIPLICATION;
                        break;
                    case '/':
                        operation = Operation.DIVISION;
                        break;
                }
                operand1 = expression.substring(0, operatorPosition);
                operand2 = expression.substring(operatorPosition + 1);
            } else if (expression.charAt(0) != '(') {
                // Number
                operation = Operation.NONE;
                number = new Integer(expression).intValue();
            } else {
                // Expression in parentheses.
                parse(expression.substring(1, expression.length() - 1));
            }
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        Operation getOperation() {
            return operation;
        }
        
        int getNumber() {
            return number;
        }
        
        String getOperand1() {
            return operand1;
        }
        
        String getOperand2() {
            return operand2;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>  
}
