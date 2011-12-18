package jadeorg.lang.simplemessages;

import jadeorg.lang.Message;

/**
 * A simple message.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class SimpleMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String content;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SimpleMessage(int performative) {
        super(performative);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public String generateContent() {
        return content;
    }
    
    @Override
    public void parseContent(String content) {
        this.content = content;
    }

    // </editor-fold>
}
