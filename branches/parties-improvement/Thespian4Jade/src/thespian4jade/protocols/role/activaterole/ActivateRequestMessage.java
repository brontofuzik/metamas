/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thespian4jade.protocols.role.activaterole;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;

/**
 * An 'Activate request' message.
 * An 'Activate request' message is a message sent from a player to a role
 * containing a request to activate a particular role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @verison %I% %G%
 */
public class ActivateRequestMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ActivateRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public String generateContent() {
        return "activate-role";
    }

    @Override
    public void parseContent(String content) {
        // Do nothing.
    }
    
    // </editor-fold>
}
