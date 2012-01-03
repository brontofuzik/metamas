package jadeorg.core.organization.power;

import jadeorg.core.organization.Role;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A simple power.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public abstract class SimplePower extends OneShotBehaviourState implements Power {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SimplePower(String name) {
        super(name);
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
    
    protected Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold> 
}
