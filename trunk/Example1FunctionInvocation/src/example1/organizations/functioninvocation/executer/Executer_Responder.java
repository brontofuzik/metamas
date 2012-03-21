package example1.organizations.functioninvocation.executer;

import example1.protocols.invokefunctionprotocol.InvokeFunctionProtocol;
import thespian4jade.proto.Responder;

/**
 * The Executer role responder.
 * @author Lukáš Kúdela
 * @since 2012-01-05
 * @version %I% %G%
 */
class Executer_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Executer_Responder() {
        addResponder(InvokeFunctionProtocol.getInstance());
    }
     
    // </editor-fold>
}
