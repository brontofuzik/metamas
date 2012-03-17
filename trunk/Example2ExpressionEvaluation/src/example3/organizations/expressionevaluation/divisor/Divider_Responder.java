package example3.organizations.expressionevaluation.divisor;

import example3.protocols.evaluatedivision.EvaluateDivisionProtocol;
import thespian4jade.core.Responder;

/**
 * The 'Divisor' role responder.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
class Divider_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Divider_Responder class.
     */
    Divider_Responder() {
        addResponder(EvaluateDivisionProtocol.getInstance());
    }
    
    // </editor-fold>    
}