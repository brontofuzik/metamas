package jadeorg.proto.roleprotocol.activateprotocol;

import jadeorg.lang.Message;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;

/**
 * An 'Activate reply' message.
 * An 'Activate reply' message is a message sent from a role to a player
 * containing the reply to a request to activate the role.
 * @author Lukáš Kúdela
 * @since 2011-11-11
 * @version %I% %G%
 */
public class ActivateReplyMessage extends Message {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private boolean agree;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public ActivateReplyMessage setAgree(boolean agree) {
        this.agree = agree;
        return this;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected int getPerformative() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected MessageParser createParser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected MessageGenerator createGenerator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>   
}
