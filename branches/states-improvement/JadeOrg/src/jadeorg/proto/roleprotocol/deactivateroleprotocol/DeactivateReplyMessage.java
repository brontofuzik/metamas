package jadeorg.proto.roleprotocol.deactivateroleprotocol;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;

/**
 * An 'Deactivate reply' message.
 * An 'Deactivate reply' message is a message sent from a role to a player
 * containing the reply to a request to deactivate the role.
 * @author Lukáš Kúdela
 * @since 2011-11-11
 * @version %I% %G%
 */
public class DeactivateReplyMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private boolean agree;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public DeactivateReplyMessage setAgree(boolean agree) {
        this.agree = agree;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageTemplate createPerformativeTemplate() {
        return MessageTemplate.or(
            MessageTemplate.MatchPerformative(ACLMessage.AGREE),
            MessageTemplate.MatchPerformative(ACLMessage.REFUSE));
    }

    @Override
    protected MessageParser createParser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected MessageGenerator createGenerator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
}
