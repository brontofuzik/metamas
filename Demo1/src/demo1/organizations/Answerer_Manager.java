package demo1.organizations;

import demo1.organizations.DemoOrganization.Answerer;
import demo1.protocols.calculatefactorialprotocol.CalculateFactorialProtocol;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
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

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MessageTemplate template =
                MessageTemplate.and(
                    CalculateFactorialProtocol.getInstance().getTemplate(),
                    MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
                 
            ACLMessage message = myAgent.receive(template);          
            if (message != null) {
                myAgent.putBack(message);
                getMyAnswerer().respondToCalculateFactorial(message.getConversationId(), message.getSender());
        }
        
        // </editor-fold>
    }
    
        // </editor-fold>
    }
}
