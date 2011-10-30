package jadeorg.behaviours;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author Lukáš Kúdela (2011-10-12)
 */
public class _NameResponderBehaviour extends CyclicBehaviour {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private final String WHAT_IS_YOUR_NAME_QUESTION = "What is your name?";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private MessageTemplate template = MessageTemplate.MatchContent(WHAT_IS_YOUR_NAME_QUESTION);
    
    private String name;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public _NameResponderBehaviour(String name) {
        assert !name.isEmpty();
        
        this.name = name;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        ACLMessage message = myAgent.receive(template);
        if (message != null) {
            // A message was received.
            ACLMessage reply = message.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            reply.setContent(name);
            myAgent.send(reply);
        } else {
            // No message was received.
            block();
        }
    } 
        
    // </editor-fold>   
}
