package metamas.semanticmodel.player;

import java.util.HashMap;
import java.util.Map;
import metamas.utilities.Assert;

/**
 * An player class.
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class PlayerClass {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;
    
    private Map<String, Requirement> responsibilities = new HashMap<String, Requirement>();

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public PlayerClass(String name) {
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

    public void addRequirement(Requirement requirement) {
        // ----- Preconditions -----
        Assert.isNotNull(requirement, "requirement");
        // -------------------------
        
        responsibilities.put(requirement.getName(), requirement);
    }
    
    public Player createPlayer(String name) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------
        
        return new Player(name, this);
    }

    // </editor-fold>
}
