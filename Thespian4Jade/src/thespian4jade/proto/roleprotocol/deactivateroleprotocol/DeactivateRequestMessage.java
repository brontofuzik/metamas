package thespian4jade.proto.roleprotocol.deactivateroleprotocol;

import jade.lang.acl.ACLMessage;
import thespian4jade.lang.TextMessage;

/**
 * A 'Deactivate request' message.
 * A 'Deactivate request' message is a message sent from a player to a role
 * containing a request to deactivate a partcular role.
 * DP: Abstract factory - Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class DeactivateRequestMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public DeactivateRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        return "deactivate";
    }

    @Override
    public void parseContent(String content) {
        // Do nothing.
    }
    
    // </editor-fold>
}
