/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.roleprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;

/**
 * An 'Activate request' message.
 * An 'Activate request' message is a message sent from a player to a role
 * containing a request to activate a particular role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @verison %I% %G%
 */
public class ActivateRequestMessage extends RoleMessage {

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    protected int getPerformative() {
        return ACLMessage.REQUEST;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected MessageParser createParser() {
        return new ActivateRequestMessageParser();
    }

    @Override
    protected MessageGenerator createGenerator() {
        return new ActivateRequestMessageGenerator();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Activate request' message parser.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private class ActivateRequestMessageParser extends MessageParser {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public Message parse(ACLMessage message) {
            // TODO Implement.
            throw new UnsupportedOperationException("Not supported yet.");
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Activate request' message generator.
     * DP: Singleton - Singleton
     * DP: Abstract factory - Concrete product
     * @author Lukáš Kúdela
     * @since 2011-11-06
     * @version %I% %G%
     */
    private class ActivateRequestMessageGenerator extends MessageGenerator {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public ACLMessage generate(Message message) {
            // TODO Implement.
            throw new UnsupportedOperationException("Not supported yet.");
        }
                
        // </editor-fold>
    }
    
    // </editor-fold>
}
