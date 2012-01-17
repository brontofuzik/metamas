package demo1.organizations;

import demo1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import jadeorg.core.Responder;

/**
 * The Asker role responder.
 * @author Lukáš Kúdela
 * @since 2012-01-05
 * @version %I% %G%
 */
public class Answerer_Responder extends Responder {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Answerer_Responder() {
        addResponder(CalculateFactorialProtocol.getInstance());
    }
     
    // </editor-fold>
}
