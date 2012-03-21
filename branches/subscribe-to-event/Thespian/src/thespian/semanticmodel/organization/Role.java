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
public class Role {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;
    
    private Map<String, Competence> competences = new HashMap<String, Competence>();
    
    /** The FSM representing the role*/
    private FSM fsm;

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
    
    public FSM getFSM() {
        return fsm;
    }
    
    public void setFSM(FSM fsm) {
        this.fsm = fsm;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addCompetence(Competence competence) {
        // ----- Preconditions -----
        Assert.isNotNull(competence, "competence");
        // -------------------------
        
        competences.put(competence.getName(), competence);
    }
    
    // </editor-fold>


}
