package thespian.semanticmodel.organization;

import java.util.HashMap;
import java.util.Map;
import thespian.semanticmodel.fsm.FSM;
import thespian.utilities.Assert;

/**
 * A role class.
 * @author Lukáš Kúdela
 * @since 2012-01-12
 * @version %I% %G%
 */
public class RoleClass {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;
    
    private Map<String, Power> powers = new HashMap<String, Power>();
    
    /** The FSM representing the role*/
    private FSM fsm;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public RoleClass(String name) {
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
    
    public FSM getFSM() {
        return fsm;
    }
    
    public void setFSM(FSM fsm) {
        // TODO
        throw new UnsupportedOperationException("Not yet implemented");
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
