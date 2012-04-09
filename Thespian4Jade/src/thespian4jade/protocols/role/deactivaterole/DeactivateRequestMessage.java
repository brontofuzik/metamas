package thespian4jade.protocols.role.deactivaterole;

import jade.lang.acl.ACLMessage;
import thespian4jade.language.TextMessage;

/**
 * An 'Deactivate request' message is a message sent by the 'Deactivate role'
 * protocol initiator party (a player) to the responder party (a role) and
 * contains a request to deactivate that particular role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class DeactivateRequestMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the DeactivateRequestMessage class.
     * The corresponding ACL message has the REQUEST performative.
     */
    public DeactivateRequestMessage() {
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
        return "deactivate-role";
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
