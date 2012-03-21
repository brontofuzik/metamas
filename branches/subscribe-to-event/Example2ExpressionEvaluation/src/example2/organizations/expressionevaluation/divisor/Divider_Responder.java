package example2.organizations.expressionevaluation.divisor;

import example2.protocols.Protocols;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Responder;

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
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.EVALUATE_DIVISION_PROTOCOL));
    }
    
    // </editor-fold>    
}