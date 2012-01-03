package jadeorg.core.player.requirement;

import jadeorg.core.player.Player;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A simple requirement.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public abstract class SimpleRequirement extends OneShotBehaviourState implements Requirement {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public SimpleRequirement(String name) {
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
    
    protected Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
}
