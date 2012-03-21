package thespian4jade.lang;

/**
 * A simple message.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class SimpleMessage extends TextMessage {
    
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
    
    // <editor-fold defaultstate="collapsed" desc="Classes">

    /**
     * A simple message factory.
     * @author Lukáš Kúdela
     * @since
     * @version %I% %G%
     */
    public static class Factory implements IMessageFactory {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /**
         * The performative.
         */
        private int performative;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the Factory class.
         * @param performative the performative
         */
        public Factory(int performative) {
            this.performative = performative;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Creates an empty simple message.
         * @return an empty simple message
         */
        @Override
        public Object createMessage() {
            return new SimpleMessage(performative);
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
