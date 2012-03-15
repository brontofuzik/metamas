package example3.organizations.expressionevaluation.evaluator;

import jade.core.AID;
import thespian4jade.core.organization.power.FSMPower;

/**
 * The 'Evaluate' (FSM) competence.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Evaluate_Competence extends FSMPower<String, Integer> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The AID of the binary evaluator initiating the protocol.
     */
    private AID binaryEvaluatorAID;
    
    /**
     * The expression to evaluate.
     */
    private String expression;
    
    /**
     * The value of the expression.
     */
    private int value;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Evaluate_Competence class.
     */
    public Evaluate_Competence() {
        
    }
    
    // </editor-fold>
}
