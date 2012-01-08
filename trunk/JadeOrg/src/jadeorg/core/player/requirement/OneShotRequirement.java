package jadeorg.core.player.requirement;

import jadeorg.core.player.Player;
import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * A one-shot requirement.
 * @author Lukáš Kúdela
 * @since 2011-01-02
 * @version %I% %G%
 */
public abstract class OneShotRequirement<TArgument, TResult> extends OneShotBehaviourState
    implements Requirement<TArgument, TResult> {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private TArgument argument;
    
    private TResult result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public OneShotRequirement(String name) {
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
    
    protected void setResult(TResult result) {
        this.result = result;
    }
    
    protected Player getMyPlayer() {
        return (Player)myAgent;
    }
    
    // </editor-fold>
}
