package example2.organizations.expressionevaluation.evaluator;

import example2.protocols.Protocols;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.Responder;

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
        // Add protocols.
        addProtocol(ProtocolRegistry.getProtocol(Protocols.EVALUATE_EXPRESSION_PROTOCOL));
    }
    
    // </editor-fold>  
}
