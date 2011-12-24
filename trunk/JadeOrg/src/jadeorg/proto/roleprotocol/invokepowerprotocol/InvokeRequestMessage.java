package jadeorg.proto.roleprotocol.invokepowerprotocol;

import jadeorg.lang.StringMessage;


/**
 * TODO
 * @author Lukáš Kúdela
 * @since 2011-11-09
 * @version %I% %G%
 */
public class InvokeRequestMessage extends StringMessage {

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    InvokeRequestMessage() {
        super(0);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getPower() {
        return "";
    }
    
    public String[] getArgs() {
        return new String[0];
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
