package example1.organizations.functioninvocation.executor;

import example1.protocols.Protocols;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.Responder;

/**
 * The Executer role responder.
 * @author Lukáš Kúdela
 * @since 2012-01-05
 * @version %I% %G%
 */
class Executer_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Executer_Responder() {
        addProtocol(ProtocolRegistry.getProtocol(Protocols.INVOKE_FUNCTION_PROTOCOL));
    }
     
    // </editor-fold>
}
