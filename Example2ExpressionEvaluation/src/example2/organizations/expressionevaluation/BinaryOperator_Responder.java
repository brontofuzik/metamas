package example2.organizations.expressionevaluation;

import example2.protocols.Protocols;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.Responder;

/**
 * The 'Binary operator' role responder.
 * @author Lukáš Kúdela
 * @since 2012-03-24
 * @version %I% %G%
 */
public class BinaryOperator_Responder extends Responder {
    
    /**
     * Initializes a new instance of the BinaryOperator_Responder class.
     */
    BinaryOperator_Responder() {
        // Add protocols.
        addProtocol(ProtocolRegistry.getProtocol(Protocols.EVALUATE_BINARY_OPERATION_PROTOCOL));
    }
}
