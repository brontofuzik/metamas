package jadeorg.core.organization.power;

import jadeorg.core.organization.Role;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A simple power.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public abstract class SimplePower<TArgument, TResult> extends OneShotBehaviourState
    implements Power<TArgument, TResult> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private TArgument argument;
    
    private TResult result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SimplePower(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public void setArgument(TArgument argument) {
        this.argument = argument;
    }
    
    public TResult getResult() {
        return result;
    }
    
    // ----- PROTECTED -----
    
    protected TArgument getArgument() {
        return argument;
    }
    
    public void setResult(TResult result) {
        this.result = result;
    }
    
    // ----- PROTECTED -----
    
    protected Role getMyRole() {
        return (Role)myAgent;
    }
    
    // </editor-fold> 
}