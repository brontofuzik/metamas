package thespian.semanticmodel.protocol;

import thespian.utilities.Assert;

/**
 * A message.
 * @author Lukáš Kúdela
 * @since 2012-01-12
 * @version %I% %G%
 */
public class Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private MessageType type;
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Message(String name, MessageType type) {
        // ----- Preconditions ----- 
        Assert.isNotEmpty(name, "name");
        // -------------------------   
        
        this.name = name;
        this.type = type;
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Enums">
    
    public enum MessageType
    {
        TextMessage,
        BinaryMessage
    }
    
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public String getName() {
        return name;
    }
    
    // </editor-fold>    
}
