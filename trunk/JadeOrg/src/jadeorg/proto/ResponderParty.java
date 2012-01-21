package jadeorg.proto;

import jade.lang.acl.ACLMessage;

/**
 * A responder party.
 * @author Lukáš Kúdela
 * @since 2012-01-09
 * @version %I% %G%
 */
public abstract class ResponderParty extends Party {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected ResponderParty(Protocol protocol, ACLMessage aclMessage) {
        super(protocol);
        // ----- Preconditions -----
        assert aclMessage != null && aclMessage.getConversationId() != null
            && !aclMessage.getConversationId().isEmpty();
        // -------------------------
        
        setProtocolId(aclMessage.getConversationId());
    }
    
    // </editor-fold>
}