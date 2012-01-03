package jadeorg.core.organization.power;

import jadeorg.core.organization.Role;
import jadeorg.proto.Party;
import jadeorg.proto.PartyState;

/**
 * A complex power.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public abstract class ComplexPower extends PartyState implements Power {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ComplexPower(String name, Party party) {
        super(name, party);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public Object getArgument() {
        return argument;
    }
    
    public void setArgument(Object argument) {
        this.argument = argument;
    }
    
    public Object getResult() {
        return result;
    }
    
    public void setResult(Object result) {
        this.result = result;
    }
    
    // ----- PROTECTED -----
        
    protected Role getMyPlayer() {
        return (Role)myAgent;
    }
    
    // </editor-fold>
}
