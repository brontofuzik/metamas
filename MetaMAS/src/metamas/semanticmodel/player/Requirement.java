package metamas.semanticmodel.player;

import metamas.utilities.Assert;

/**
 * A requirement.
 * @author Lukáš Kúdela
 * @since 2012-01-11
 * @version %I% %G%
 */
public class Requirement {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private final String name;
    
    private final RequirementType type;
    
    private final String argumentType;
    
    private final String resultType;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Requirement(String name, RequirementType type, String argumentType,
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
    
    public enum RequirementType
    {
        OneShotRequirement,
        FSMRequirement
    }
    
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return name;
    }
    
    public RequirementType getType() {
        return type;
    }
    
    // </editor-fold>
}
