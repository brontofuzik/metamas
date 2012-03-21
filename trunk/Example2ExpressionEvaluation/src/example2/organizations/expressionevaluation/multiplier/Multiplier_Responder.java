package example2.organizations.expressionevaluation.multiplier;

import example2.protocols.evaluatemultiplication.EvaluateMultiplicationProtocol;
import thespian4jade.proto.Responder;

/**
 * The 'Multiplier' role responder.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
class Multiplier_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Multiplier_Responder class.
     */
    Multiplier_Responder() {
        addResponder(EvaluateMultiplicationProtocol.getInstance());
    }
    
    // </editor-fold> 
}
