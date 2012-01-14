package demo1.organizations;

import demo1.organizations.Demo_Organization.Answerer;
import demo1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import jade.lang.acl.ACLMessage;
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
        addResponder(new CalculateFactorialHandler());
    }
     
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    private Answerer getMyAnswerer() {
        return (Answerer)myAgent;        
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class CalculateFactorialHandler extends ResponderWrapper {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        CalculateFactorialHandler() {
            super(CalculateFactorialProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(ACLMessage message) {
            getMyAnswerer().logInfo(String.format("Responding to the 'Calculate factorial' protocol (id = %1$s).",
                message.getConversationId()));
        
            getMyAnswerer().addBehaviour(new Answerer_CalculateFactorialResponder(message));
        }
    
        // </editor-fold>
    }
    
    // </editor-fold>
}
