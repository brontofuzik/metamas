package metamas.semanticmodel.interactionprotocol;

import java.util.HashMap;
import java.util.Map;
import metamas.semanticmodel.Role;
import metamas.utilities.Assert;

/**
 * An interaction protocol party.
 * @author Lukáš Kúdela
 */
public class Party {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private Role role;
    
    private State startState;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Party(String name, Role role) {
        Assert.isNotEmpty(name, "name");
        Assert.isNotNull(role, "role");
        
        this.name = name;
        this.role = role;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
    
    public State getStartState() {
        return startState;
    }
    
    public void setStartState(State state) {
        Assert.isNotNull(state, "state");
        
        this.startState = state;
    }
    
    // </editor-fold>
}
