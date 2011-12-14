package jadeorg.lang.simplemessages;

import jadeorg.lang.simplemessages.SimpleMessage;
import jade.lang.acl.ACLMessage;

/**
 * A 'Refuse' message.
 * @author Lukáš Kúdela
 * @since 2011-12-14
 * @version %I% %G%
 */
public class RefuseMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RefuseMessage() {
        super(ACLMessage.REFUSE);
    }
    
    // </editor-fold>    
}
