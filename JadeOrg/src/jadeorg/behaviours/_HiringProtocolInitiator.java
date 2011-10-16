package jadeorg.behaviours;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 *
 * @author Lukáš Kúdela (2011-10-12)
 */
public class _HiringProtocolInitiator extends Behaviour {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private final String CONVERSATION_ID = "Hiring-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String groupName;
    
    private String positionName;
    
    private State state;
    
    private MessageTemplate messageTemplate;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public _HiringProtocolInitiator(String groupName, String positionName) {
        assert !groupName.isEmpty();
        assert !positionName.isEmpty();
        
        this.groupName = groupName;
        this.positionName = positionName;
    }

    // </editor-fold>
    
    private enum State {
        BEGINNING,
        WAITING_FOR_REPLY,
        
        END,
    }

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        switch (state) {
           case BEGINNING:
               actionBeginning();
               break;
               
           case WAITING_FOR_REPLY:
               actionWaitingForReply();
               break;
               
           case END:
               actionEnd();
               break;
               
           default:
               assert false;
        }
    }

    @Override
    public boolean done() {
        return state == State.END;
    }
    
    // ---------- PRIVATE ----------
    
    // Active state
    private void actionBeginning() {
        // Create the message to send.
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(new AID(groupName, AID.ISLOCALNAME));
        message.setContent(positionName);
        message.setConversationId(CONVERSATION_ID);
        message.setReplyWith("REQUEST"+System.currentTimeMillis());
        
        // Send the message.
        myAgent.send(message);
        
        // Create the message template used to receive replies.
        messageTemplate = MessageTemplate.and(
            MessageTemplate.MatchConversationId(CONVERSATION_ID),
            MessageTemplate.MatchInReplyTo(message.getReplyWith()));
        
        // Transition to the next state.
        state = State.WAITING_FOR_REPLY;
    }
    
    // Passive state
    private void actionWaitingForReply() {
        ACLMessage reply = myAgent.receive();
        if (reply != null) {
            if (reply.getPerformative() == ACLMessage.QUERY_IF) {
                handleAccept(reply);
            } else if (reply.getPerformative() == ACLMessage.REFUSE) {
                handleRefuse(reply);
            } else {
                
            }
        } else {
            block();
        }
    }
    
    private void handleAccept(ACLMessage reply) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleRefuse(ACLMessage reply) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    
    
    
    // Active state
    private void actionEnd() {
    }
    
    // </editor-fold>
}
