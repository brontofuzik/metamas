package demo1.organizations;

import demo1.organizations.Demo_Organization.Answerer;
import demo1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import jade.lang.acl.ACLMessage;
import jadeorg.util.ManagerBehaviour;

/**
 * The Asker role manager behaviour.
 * @author Lukáš Kúdela
 * @since 2012-01-05
 * @version %I% %G%
 */
public class Answerer_Manager extends ManagerBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Answerer_Manager() {
        addHandler(new CalculateFactorialHandler());
    }
     
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    private Answerer getMyAnswerer() {
        return (Answerer)myAgent;        
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class CalculateFactorialHandler extends HandlerBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        CalculateFactorialHandler() {
            super(CalculateFactorialProtocol.getInstance());
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void handleMessage(ACLMessage message) {
            getMyAnswerer().respondToCalculateFactorial(message);
        }
    
        // </editor-fold>
    }
    
    // </editor-fold>
}
