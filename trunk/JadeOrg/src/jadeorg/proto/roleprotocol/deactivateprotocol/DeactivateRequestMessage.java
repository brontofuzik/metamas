package jadeorg.proto.roleprotocol.deactivateprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.roleprotocol.RoleMessage;

/**
 * A 'Deactivate request' message.
 * A 'Deactivate request' message is a message sent from a player to a role
 * containing a request to deactivate a partcular role.
 * DP: Abstract factory - Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class DeactivateRequestMessage extends RoleMessage {

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    protected int getPerformative() {
        return ACLMessage.REQUEST;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Creates the 'Deactivate request' message parser.
     * @return the 'Deactivate request' message parser
     */
    @Override
    protected MessageParser createParser() {
        return new DeactivateRequestMessageParser();
    }

    /**
     * Creates the 'Deactivate request' message generator.
     * @return the 'Deactivate request' message generator
     */
    @Override
    protected MessageGenerator createGenerator() {
        return new DeactivateRequestMessageGenerator();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Deactivate request' message parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private static class DeactivateRequestMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public Message parse(ACLMessage message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Deactivate request' message generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private static class DeactivateRequestMessageGenerator extends MessageGenerator {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public ACLMessage generate(Message message) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
