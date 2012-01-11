package jadeorg.core.player.requirement;

import jadeorg.core.player.Player;
import jadeorg.proto.jadeextensions.FSMBehaviourState;

/**
 * A FSM requirement.
 * @author Lukáš Kúdela
 * @since 2012-01-02
 * @version %I% %G%
 */
public class FSMRequirement<TArgument, TResult> extends FSMBehaviourState
    implements Requirement<TArgument, TResult> {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private TArgument argument;
    
    private TResult result;
    
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
