package jadeorg.lang.simplemessages;

import jadeorg.lang.simplemessages.SimpleMessage;
import jade.lang.acl.ACLMessage;

/**
 * An 'Agree' message.
 * @author Lukáš Kúdela
 * @since 2011-12-14
 * @version %I% %G%
 */
public class AgreeMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public AgreeMessage() {
        super(ACLMessage.AGREE);
    }
    
    // </editor-fold>
}
