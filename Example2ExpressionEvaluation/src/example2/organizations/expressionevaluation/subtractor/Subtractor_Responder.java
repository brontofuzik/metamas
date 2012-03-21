package example2.organizations.expressionevaluation.subtractor;

import example2.protocols.Protocols;
import thespian4jade.proto.ProtocolRegistry_StaticClass;
import thespian4jade.proto.Responder;

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
        addResponder(ProtocolRegistry_StaticClass.getProtocol(Protocols.EVALUATE_SUBTRACTION_PROTOCOL));
    }
    
    // </editor-fold> 
}
