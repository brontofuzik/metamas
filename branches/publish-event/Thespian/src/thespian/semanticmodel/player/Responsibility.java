package thespian.semanticmodel.player;

import thespian.utilities.Assert;

/**
 * A responsibility.
 * @author Lukáš Kúdela
 * @since 2012-01-11
 * @version %I% %G%
 */
public class Responsibility {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private final String name;
    
    private final ResponsibilityType type;
    
    private final String argumentType;
    
    private final String resultType;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Responsibility(String name, ResponsibilityType type, String argumentType,
        String resultType) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        Assert.isNotEmpty(argumentType, "argumentType");
        Assert.isNotEmpty(resultType, "resultType");
        // -------------------------
        
        this.name = name;
        this.type = type;
        this.argumentType = argumentType;
        this.resultType = resultType;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Enums">
    
    public enum ResponsibilityType
    {
        Synchronous,
        Asynchronous
    }
    
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return name;
    }
    
    public ResponsibilityType getType() {
        return type;
    }
    
    // </editor-fold>
}
