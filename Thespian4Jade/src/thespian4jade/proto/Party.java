package thespian4jade.proto;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import thespian4jade.lang.Message;
import thespian4jade.proto.jadeextensions.FSMBehaviourState;

/**
 * A protocol party.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class Party<TAgent extends Agent> extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Protocol protocol;
    
    private String protocolId;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Party(Protocol protocol) {
        // ----- Preconditions -----
        assert protocol != null;
        // -------------------------
        
        this.protocol = protocol;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the id of the protocol.
     * @return the id of the protocol
     */
    public String getProtocolId() {
        return protocolId;
    }
    
    /**
     * Sets the id of the protocol.
     * @param protocolId the id of the protocol
     */
    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }
    
    // ----- PROTECTED -----
    
    /**
     * Gets my agent.
     * @return my agent
     */
    protected TAgent getMyAgent() {
        return (TAgent)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sends a JadeOrg message.
     * @param messageClass the message class
     * @param receivers the receivers. More precisely, their AIDs
     */
    public void send(Message message, AID[] receivers) {
        // Generate the ACL message.
        ACLMessage aclMessage = message.generateACLMessage();      
        aclMessage.setProtocol(protocol.getName());
        aclMessage.setConversationId(protocolId);
        for (AID receiver : receivers) {
            aclMessage.addReceiver(receiver);
        }
        
        //System.out.println("SENDING MESSAGE: " + aclMessage.toString());
       
        // Send the ACL message.
        myAgent.send(aclMessage);
    }
    
    /**
     * Receives a JadeOrg message.
     * @param messageClass the message class
     * @return the received message
     */
    public boolean receive(Message message, AID[] senders) {
        MessageTemplate protocolTemplate = createProtocolTemplate();
        MessageTemplate sendersTemplate = createSendersTemplate(senders);
        
        MessageTemplate messageTemplate = MessageTemplate.and(
            MessageTemplate.MatchPerformative(message.getPerformative()),
            MessageTemplate.and(protocolTemplate, sendersTemplate));        
         
        // Receive the ACL message.
        ACLMessage aclMessage = myAgent.receive(messageTemplate);      
        if (aclMessage != null) {
            //System.out.println("RECEIVED MESSAGE: " + aclMessage.toString());
            
            // Parse the ACL message.
            message.setSender(aclMessage.getSender());
            message.parseACLMessage(aclMessage);
            return true;
        } else {
            return false;
        }
    }
    
    // ----- PRIVATE -----
    
    /**
     * Creates a message template that matches the protocol.
     * @return the message template that matches the protocol
     */
    private MessageTemplate createProtocolTemplate() {
        return MessageTemplate.and(
            MessageTemplate.MatchProtocol(protocol.getName()),
            MessageTemplate.MatchConversationId(protocolId));
    }
    
    /**
     * Creates a message template that matches the senders.
     * @param seners the senders. More precisely, their AIDs
     * @return the message template that matches the senders
     */
    private MessageTemplate createSendersTemplate(AID[] senders) {
        // ----- Preconditions -----
        assert senders != null && senders.length > 0;
        // -------------------------
        
        MessageTemplate sendersTemplate = MessageTemplate.MatchSender(senders[0]);
        for (int i = 1; i < senders.length; i++) {
            sendersTemplate = MessageTemplate.or(
                sendersTemplate,
                MessageTemplate.MatchSender(senders[i]));
        }
        return sendersTemplate;
    }
    
    // </editor-fold>
}
