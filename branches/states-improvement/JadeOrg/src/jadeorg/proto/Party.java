package jadeorg.proto;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.core.organization.Organization;
import jadeorg.lang.Message;
import jadeorg.proto.State.Event;

/**
 * A protocol party.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class Party extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Party(String name) {
        setBehaviourName(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return getBehaviourName();
    }
    
    protected abstract Protocol getProtocol();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sends a JadeOrg message.
     * @param messageClass the message class
     * @param message the message
     */
    public void send(Class messageClass, Message message) {
        // Generate the ACL message.
        ACLMessage aclMessage = getProtocol().generate(messageClass, message);
        
        System.out.println("SENDING MESSAGE: " + aclMessage.toString());
        myAgent.send(aclMessage);
    }
    
    // TAG DEBUGGING
    public void sendACLMessage(ACLMessage aclMessage) {        
        myAgent.send(aclMessage);
    }
    
    /**
     * Receives a JadeOrg message.
     * @param messageClass the message class
     * @return the received message
     */
    public Message receive(Class messageClass, AID senderAID) {
        MessageTemplate messageTemplate = null;
        
        // Constrain the message class if specified.
        if (messageClass != null) {
            messageTemplate = getProtocol().getTemplate(messageClass);
        }
             
        // Constrain the sender if specified.
        if (senderAID != null) {
            MessageTemplate senderMessageTemplate = MessageTemplate.MatchSender(senderAID);
            if (messageTemplate != null) {
                messageTemplate = MessageTemplate.and(messageTemplate, senderMessageTemplate);
            } else {
                messageTemplate = senderMessageTemplate;
            }
        }
                
        // Receive the ACL message.
        ACLMessage aclMessage = null;
        if (messageTemplate != null) {
            aclMessage = myAgent.receive(messageTemplate);
        } else {
            aclMessage = myAgent.receive();
        }
        
        if (aclMessage != null) {
            // Parse the ACL message.
            System.out.println("RECEIVED MESSAGE: " + aclMessage.toString());
            return getProtocol().parse(messageClass, aclMessage);
        } else {
            return null;
        }
    }
    
    public ACLMessage receiveACLMessage() {
        return myAgent.receive();
    }
    
    // ---------- PROTECTED ----------
    
    protected void registerState(State state) {
        registerState((Behaviour)state, state.getName());
        
        // TODO Move this piece of logic somewhere else.
        if (state instanceof PassiveState) {
            registerTransition(state, state, Event.LOOP);
        }
    }  
    
    protected void registerFirstState(State state) {
        registerFirstState((Behaviour)state, state.getName());
        
        // TODO Move this piece of logic somewhere else.
        if (state instanceof PassiveState) {
            registerTransition(state, state, Event.LOOP);
        }
    }
    
    protected void registerLastState(State state) {
        registerLastState((Behaviour)state, state.getName());
        
        // TODO Move this piece of logic somewhere else.
        if (state instanceof PassiveState) {
            registerTransition(state, state, Event.LOOP);
        }
    }
    
    protected void registerTransition(State fromState, State toState, Event event) {
        registerTransition(fromState.getName(), toState.getName(), event.getCode());
    }
    
    protected void registerDefaultTransition(State fromState, State toState) {
        registerDefaultTransition(fromState.getName(), toState.getName());
    }
    
    // </editor-fold>
}
