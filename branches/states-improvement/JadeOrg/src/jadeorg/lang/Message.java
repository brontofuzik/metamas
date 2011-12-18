package jadeorg.lang;

/**
 * A message in an interaction protocol.
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public abstract class Message {
    
    private int performative;
    
    protected Message(int performative) {
        this.performative = performative;
    }
    
    public int getPerformative() {
        return performative;
    }
    
    public void setPerformative(int performative) {
        this.performative = performative;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public abstract String generateContent();
    
    public abstract void parseContent(String content);
    
    // </editor-fold>
}
