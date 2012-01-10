package metamas.semanticmodel.protocol;

import metamas.utilities.Assert;

/**
 * A interaction protocol message.
 * @author Lukáš Kúdela
 */
public class Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Message(String name) {
       Assert.isNotEmpty(name, "name"); 
           
       this.name = name;
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public String getName() {
        return name;
    }
    
    // </editor-fold>    
}
