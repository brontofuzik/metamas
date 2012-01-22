package example1.organizations.demo.answerer;

import example1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import jadeorg.core.Responder;

/**
 * The Asker role responder.
 * @author Luk� K�dela
 * @since 2012-01-05
 * @version %I% %G%
 */
public class Answerer_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Answerer_Responder() {
        addResponder(CalculateFactorialProtocol.getInstance());
    }
     
    // </editor-fold>
}