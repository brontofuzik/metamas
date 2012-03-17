package thespian.semanticmodel.player;

import java.util.HashMap;
import java.util.Map;
import thespian.utilities.Assert;

/**
 * An player type.
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class PlayerType {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;
    
    private Map<String, Responsibility> responsibilities = new HashMap<String, Responsibility>();

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public PlayerType(String name) {
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

    public void addResponsibility(Responsibility responsibility) {
        // ----- Preconditions -----
        Assert.isNotNull(responsibility, "responsibility");
        // -------------------------
        
        responsibilities.put(responsibility.getName(), responsibility);
    }
    
    public Player createPlayer(String name) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------
        
        return new Player(name, this);
    }

    // </editor-fold>
}
