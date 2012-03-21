package example2.organizations.expressionevaluation.adder;

import example2.protocols.Protocols;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Responder;

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
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.EVALUATE_ADDITION_PROTOCOL));
    }
    
    // </editor-fold>
}
