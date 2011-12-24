/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.roleprotocol.activateroleprotocol;

import jade.lang.acl.ACLMessage;
import jadeorg.lang.StringMessage;

/**
 * An 'Activate request' message.
 * An 'Activate request' message is a message sent from a player to a role
 * containing a request to activate a particular role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @verison %I% %G%
 */
public class ActivateRequestMessage extends StringMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ActivateRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        return "activate";
    }

    @Override
    public void parseContent(String content) {
        // Do nothing.
    }
    
    // </editor-fold>
}
