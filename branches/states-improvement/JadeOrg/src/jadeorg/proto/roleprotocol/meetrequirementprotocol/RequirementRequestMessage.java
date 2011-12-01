package jadeorg.proto.roleprotocol.meetrequirementprotocol;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.roleprotocol.RoleMessage;

/**
 * A 'Requirement request' message.
 * A 'Requirement request' message is a message sent from a role to a player
 * containing ... TODO Finish.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class RequirementRequestMessage extends RoleMessage {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String requirement;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getRequirement() {
        return requirement;
    }
    
    public RequirementRequestMessage setRequirement(String requirement) {
        this.requirement = requirement;
        return this;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageTemplate createPerformativeTemplate() {
        return MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
    }
    
    @Override
    protected MessageParser createParser() {
        return new RequirementRequestMessageParser();
    }

    @Override
    protected MessageGenerator createGenerator() {
        return new RequirementRequestMessageGenerator();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class RequirementRequestMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public Message parse(ACLMessage message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>
    }
    
    private class RequirementRequestMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public ACLMessage generate(Message message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
