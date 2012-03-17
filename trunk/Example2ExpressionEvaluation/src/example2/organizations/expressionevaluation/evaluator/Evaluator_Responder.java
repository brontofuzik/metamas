package example2.organizations.expressionevaluation.evaluator;

import example2.protocols.evaluateexpression.EvaluateExpressionProtocol;
import thespian4jade.core.Responder;

/**
 * The 'Evaluator' role responder.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
class Evaluator_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Evaluator_Responder class.
     */
    Evaluator_Responder() {
        addResponder(EvaluateExpressionProtocol.getInstance());
    }
    
    // </editor-fold>  
}
