package example1.organizations.functioninvocation.executor;

import example1.protocols.Protocols;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.behaviours.Responder;

/**
 * The 'Executor' role responder.
 * @author Lukáš Kúdela
 * @since 2012-01-05
 * @version %I% %G%
 */
class Executor_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Executor_Responder() {
        // Add protocols.
        addProtocol(ProtocolRegistry.getProtocol(Protocols.INVOKE_FUNCTION_PROTOCOL));
    }
     
    // </editor-fold>
}
