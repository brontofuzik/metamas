package metamas.semanticmodel.organization;

import metamas.semanticmodel.fsm.FSM;
import metamas.utilities.Assert;

/**
 * A power.
 * @author Lukáš Kúdela
 * @since 2012-01-11
 * @version %I% %G%
 */
public class Power {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private final String name;
    
    private final PowerType type;
    
    private final String argumentType;
    
    private final String resultType;
    
    private FSM fsm;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Power(String name, PowerType type, String argumentType,
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
    
    public PowerType getType() {
        return type;
    }
    
    public FSM getFSM() {
        return fsm;
    }
    
    public void setFSM(FSM calculateFactorialPowerFSM) {
        // TODO
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // </editor-fold>
}
