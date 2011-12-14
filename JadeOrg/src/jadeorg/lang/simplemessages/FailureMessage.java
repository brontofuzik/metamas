package jadeorg.lang.simplemessages;

import jadeorg.lang.simplemessages.SimpleMessage;
import jade.lang.acl.ACLMessage;

/**
 * A 'Failure' message.
 * @author Lukáš Kúdela
 * @since 2011-12-14
 * @version %I% %G%
 */
public class FailureMessage extends SimpleMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public FailureMessage() {
        super(ACLMessage.FAILURE);
    }
    
    // </editor-fold>    
}
