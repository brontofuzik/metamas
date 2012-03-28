package thespian.semanticmodel.protocol;

import thespian.semanticmodel.fsm.FSM;
import thespian.semanticmodel.fsm.State;
import thespian.semanticmodel.organization.Position;
import thespian.utilities.Assert;

/**
 * An interaction protocol party.
 * @author Lukáš Kúdela
 */
public class Party {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private Position role;
    
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

    public Position getRole() {
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
