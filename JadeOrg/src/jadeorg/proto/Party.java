package jadeorg.proto;

import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;

/**
 * A protocol party.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class Party extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String protocolId;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Party(String name) {
        setBehaviourName(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return getBehaviourName();
    }
    
    // TODO Change the access modified to protected.
    public abstract Protocol getProtocol();
    
    // ----- PROTECTED -----
    
    // TODO Replace with a constructor.
    protected void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    } 
   
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sends a JadeOrg message.
     * @param messageClass the message class
     * @param message the message
     */
    public void send(Message message, AID receiverAID) {
        // Generate the ACL message.
        ACLMessage aclMessage = message.generateACLMessage();      
        aclMessage.setProtocol(getProtocol().getName());
        aclMessage.setConversationId(protocolId);
        aclMessage.addReceiver(receiverAID);
        
        System.out.println("SENDING MESSAGE: " + aclMessage.toString());
       
        // Send the ACL message.
        myAgent.send(aclMessage);
    }
    
    /**
     * Receives a JadeOrg message.
     * @param messageClass the message class
     * @return the received message
     */
    public boolean receive(Message message, AID senderAID) {
        MessageTemplate messageTemplate =
            MessageTemplate.and(
                MessageTemplate.MatchPerformative(message.getPerformative()),
                MessageTemplate.and(
                    MessageTemplate.MatchProtocol(getProtocol().getName()),
                    MessageTemplate.and(
                        MessageTemplate.MatchConversationId(protocolId),
                        MessageTemplate.MatchSender(senderAID))));
         
        // Receive the ACL message.
        ACLMessage aclMessage = myAgent.receive(messageTemplate);      
        if (aclMessage != null) {
            System.out.println("RECEIVED MESSAGE: " + aclMessage.toString());
            
            // Parse the ACL message.
            message.parseACLMessage(aclMessage);
            return true;
        } else {
            return false;
        }
    }
    
    public ACLMessage receiveACLMessage() {
        return myAgent.receive();
    }
    
    // ---------- PROTECTED ----------
    
    protected void registerState(jadeorg.proto.jadeextensions.State state) {
        registerState((Behaviour)state, state.getName());
    }
    
    protected void registerFirstState(jadeorg.proto.jadeextensions.State state) {
        registerFirstState((Behaviour)state, state.getName());
    }
    
    protected void registerLastState(jadeorg.proto.jadeextensions.State state) {
        registerLastState((Behaviour)state, state.getName());
    }
    
    protected void registerTransition(jadeorg.proto.jadeextensions.State fromState, jadeorg.proto.jadeextensions.State toState) {
        registerTransition(fromState.getName(), toState.getName(), toState.getCode());
    }
    
    protected void registerDefaultTransition(jadeorg.proto.jadeextensions.State fromState, jadeorg.proto.jadeextensions.State toState) {
        registerDefaultTransition(fromState.getName(), toState.getName());
    }
    
    // </editor-fold>
}