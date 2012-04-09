package thespian4jade.behaviours.parties;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import thespian4jade.language.Message;
import thespian4jade.protocols.Protocol;
import thespian4jade.behaviours.states.FSMBehaviourState;

/**
 * A party---a role participating in a protocol.
 * @author Lukáš Kúdela
 * @since 2011-10-20
 * @version %I% %G%
 */
public abstract class Party<TAgent extends Agent> extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The protocol.
     */
    private Protocol protocol;
    
    /**
     * The protocol id.
     */
    private String protocolId;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Party class.
     * @param protocol 
     */
    protected Party(Protocol protocol) {
        // ----- Preconditions -----
        assert protocol != null;
        // -------------------------
        
        this.protocol = protocol;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the protocol id.
     * @return the protocol id
     */
    public String getProtocolId() {
        return protocolId;
    }
    
    /**
     * Sets the protocol id.
     * @param protocolId the protocol id
     */
    public void setProtocolId(String protocolId) {
        this.protocolId = protocolId;
    }
    
    // ----- PROTECTED -----
    
    /**
     * Gets the agent owner agent.
     * @return the owner agent
     */
    protected TAgent getMyAgent() {
        return (TAgent)myAgent;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Sends a Thespian4Jade (T4J) message.
     * @param messageClass the message class
     * @param receivers the receivers; more precisely, their AIDs
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
     * Receives a Thespian4Jade (T4J) message.
     * @param message the empty message
     * @param senders the senders; more precisely, their AIDs
     * @return <c>true</c> if the message has been received, <c>false</c> otherwise
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
     * @param seners the senders; more precisely, their AIDs
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