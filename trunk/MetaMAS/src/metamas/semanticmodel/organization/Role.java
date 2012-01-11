package metamas.semanticmodel.organization;

import java.util.HashMap;
import java.util.Map;
import metamas.utilities.Assert;

/**
 * A role.
 * @author Lukáš Kúdela
 * @since 2012-01-11
 * @version %I% %G%
 */
public class Role {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;
    
    private Map<String, Power> powers = new HashMap<String, Power>();

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Role(String name) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------
        
        this.name = name;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return name;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addPower(Power power) {
        // ----- Preconditions -----
        Assert.isNotNull(power, "power");
        // -------------------------
        
        powers.put(power.getName(), power);
    }
    
    // </editor-fold>
}
