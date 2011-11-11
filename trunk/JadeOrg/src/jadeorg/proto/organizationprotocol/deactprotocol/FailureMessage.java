package jadeorg.proto.organizationprotocol.deactprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.organizationprotocol.OrganizationMessage;

/**
 * A 'Failure' message.
 * @author Lukáš Kúdela
 * @since 2011-10-23
 * @version %I% %G%
 */
public class FailureMessage extends OrganizationMessage {

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    protected int getPerformative() {
        return ACLMessage.FAILURE;
    }
        
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected MessageParser createParser() {
        return new FailureMessageParser();
    }

    @Override
    protected MessageGenerator createGenerator() {
        return new FailureMessageGenerator();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Failure' message parser.
     */
    class FailureMessageParser extends MessageParser {

        @Override
        public Message parse(ACLMessage message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * The 'Failure' message generator.
     */
    class FailureMessageGenerator extends MessageGenerator {

        @Override
        public ACLMessage generate(Message message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
        
    // </editor-fold>
}
