package example3.organizations.expressionevaluation.adder;

import example3.protocols.evaluateaddition.EvaluateAdditionProtocol;
import thespian4jade.core.Responder;

/**
 * The 'Adder' role responder.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
class Adder_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Adder_Responder class.
     */
    Adder_Responder() {
        addResponder(EvaluateAdditionProtocol.getInstance());
    }
    
    // </editor-fold>
}
