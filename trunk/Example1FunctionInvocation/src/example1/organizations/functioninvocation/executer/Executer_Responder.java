package example1.organizations.functioninvocation.executer;

import example1.protocols.invokefunctionprotocol.InvokeFunctionProtocol;
import thespian4jade.core.Responder;

/**
 * The Executer role responder.
 * @author Lukáš Kúdela
 * @since 2012-01-05
 * @version %I% %G%
 */
public class Executer_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Executer_Responder() {
        addResponder(InvokeFunctionProtocol.getInstance());
    }
     
    // </editor-fold>
}
