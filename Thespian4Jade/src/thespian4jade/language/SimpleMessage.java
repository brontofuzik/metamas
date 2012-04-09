package thespian4jade.language;

/**
 * A simple message---a thin wrapper over Jade's ACLMessage class.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class SimpleMessage extends TextMessage {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The content of the corresponding ACL message.
     */
    private String content;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the SimpleMessage class.
     * @param performative the performative of the corresponding ACL message
     */
    public SimpleMessage(int performative) {
        super(performative);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the content of the corresponding ACL message.
     * @return the content of the ACL message
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
    public void setContent(String content) {
        this.content = content;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Generates the content of corresponding the ACL message.
     * @return the content of the corresponding ACL message
     */
    @Override
    public String generateContent() {
        return content;
    }
    
    /**
     * Parses the content of the corresponding ACL message.
     * @param content the content of the ACL message
     */
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
