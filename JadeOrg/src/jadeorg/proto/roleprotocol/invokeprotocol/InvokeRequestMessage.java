package jadeorg.proto.roleprotocol.invokeprotocol;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.lang.MessageGenerator;
import jadeorg.lang.MessageParser;
import jadeorg.proto.roleprotocol.RoleMessage;

/**
 * TODO
 * @author Lukáš Kúdela
 * @since 2011-11-09
 * @version %I% %G%
 */
public class InvokeRequestMessage extends RoleMessage {

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getPower() {
        return "";
    }
    
    public String[] getArgs() {
        return new String[0];
    }
    
    // </editor-fold>
    
    @Override
    protected MessageTemplate createPerformativeTemplate() {
        return MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
    }

    @Override
    protected MessageParser createParser() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected MessageGenerator createGenerator() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
