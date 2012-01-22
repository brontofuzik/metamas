package metamas.semanticmodel.protocol;

import metamas.semanticmodel.fsm.FSM;
import metamas.semanticmodel.fsm.State;
import metamas.semanticmodel.organization.Role;
import metamas.utilities.Assert;

/**
 * An interaction protocol party.
 * @author Lukáš Kúdela
 */
public class Party {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private Role role;
    
    private FSM fsm;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Party(String name /* , Role role */) {
        Assert.isNotEmpty(name, "name");
        Assert.isNotNull(role, "role");
        
        this.name = name;
//        this.role = role;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }
    
    public FSM getFSM() {
        return fsm;
    }
    
    public Party setFSM(FSM fms) {
        this.fsm = fsm;
        return this;
    }
    
    // </editor-fold>
}
