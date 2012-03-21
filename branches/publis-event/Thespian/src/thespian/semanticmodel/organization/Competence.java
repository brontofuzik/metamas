package thespian.semanticmodel.organization;

import thespian.semanticmodel.fsm.FSM;
import thespian.utilities.Assert;

/**
 * A competence.
 * @author Lukáš Kúdela
 * @since 2012-01-11
 * @version %I% %G%
 */
public class Competence {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private final String name;
    
    private final CompetenceType type;
    
    private final String argumentType;
    
    private final String resultType;
    
    private FSM fsm;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Competence(String name, CompetenceType type, String argumentType,
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
    
    public enum CompetenceType
    {
        Synchronous,
        Asynchronous
    }
    
    // </editor-fold>  

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return name;
    }
    
    public CompetenceType getType() {
        return type;
    }
    
    public FSM getFSM() {
        return fsm;
    }
    
    public void setFSM(FSM fsm) {
        this.fsm = fsm;
    }
    
    // </editor-fold>
}
