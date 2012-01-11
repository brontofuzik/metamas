package metamas.semanticmodel.organization;

import metamas.utilities.Assert;

/**
 * A power.
 * @author Lukáš Kúdela
 * @since 2012-01-11
 * @version %I% %G%
 */
public class Power {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private PowerType type;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Power(String name, PowerType type) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------
        
        this.name = name;
        this.type = type;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Enums">
    
    public enum PowerType
    {
        OneShotPower,
        FSMPower
    }
    
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return name;
    }
    
    // </editor-fold>
}
