/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package thespian4jade.protocols.role.activaterole;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;

/**
 * An 'Activate request' message is a message sent by the 'Activate role'
 * protocol initiator party (a player) to the responder party (a role) and
 * contains a request to activate that particular role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @verison %I% %G%
 */
public class ActivateRequestMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the ActivateRequestMessage class.
     * The corresponding ACL message has the REQUEST performative.
     */
    public ActivateRequestMessage() {
        super(ACLMessage.REQUEST);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the ACL message
     */
    @Override
    public String generateContent() {
        return "activate-role";
    }

    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    @Override
    public void parseContent(String content) {
        // Do nothing.
    }
    
    // </editor-fold>
}
