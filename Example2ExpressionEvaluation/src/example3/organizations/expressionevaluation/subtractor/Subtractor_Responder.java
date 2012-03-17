package example3.organizations.expressionevaluation.subtractor;

import example3.protocols.evaluatesubtraction.EvaluateSubtractionProtocol;
import thespian4jade.core.Responder;

/**
 * The 'Subtractor' role responder.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
class Subtractor_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Subtractor_Responder class.
     */
    Subtractor_Responder() {
        addResponder(EvaluateSubtractionProtocol.getInstance());
    }
    
    // </editor-fold> 
}
