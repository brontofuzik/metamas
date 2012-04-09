package thespian4jade.behaviours.parties;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import thespian4jade.protocols.Protocol;

/**
 * An responder party---a party that responds to a protocol.
 * @author Lukáš Kúdela
 * @since 2012-01-09
 * @version %I% %G%
 */
public abstract class ResponderParty<TAgent extends Agent>
    extends Party<TAgent> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The ACL message this responder party is responding to.
     */
    private ACLMessage message;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ResponderParty class.
     * @param protocol the protocol
     * @param message the ACL message this responder party is responding to
     */
    protected ResponderParty(Protocol protocol, ACLMessage message) {
        super(protocol);
        // ----- Preconditions -----
        assert message != null && message.getConversationId() != null
            && !message.getConversationId().isEmpty();
        // -------------------------
        
        this.message = message;
        setProtocolId(message.getConversationId());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the ACL message this responder party is responding to.
     * @return the ACL message
     */
    protected ACLMessage getACLMessage() {
        return message;
    }
    
    // </editor-fold>
}