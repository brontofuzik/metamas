package jadeorg.proto.roleprotocol.deactivateroleprotocol;

import jadeorg.lang.Message;

/**
 * A 'Deactivate request' message.
 * A 'Deactivate request' message is a message sent from a player to a role
 * containing a request to deactivate a partcular role.
 * DP: Abstract factory - Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class DeactivateRequestMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    DeactivateRequestMessage() {
        super(0);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void parseContent(String content) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
}
