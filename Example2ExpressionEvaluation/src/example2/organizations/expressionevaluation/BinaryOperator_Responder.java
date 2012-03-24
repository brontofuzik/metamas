package example2.organizations.expressionevaluation;

import example2.protocols.Protocols;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Responder;

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
        addResponder(ProtocolRegistry_StaticClass.getProtocol(
            Protocols.EVALUATE_BINARY_OPERATION_PROTOCOL));
    }
}
