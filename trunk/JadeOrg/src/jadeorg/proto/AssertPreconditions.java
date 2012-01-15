package jadeorg.proto;

import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * The 'Assert preconditions' (one-shot) state.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class AssertPreconditions extends OneShotBehaviourState {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private boolean preconditionsSatisfied;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods"> 
    
    @Override
    public void action() {
        preconditionsSatisfied = preconditionsSatisfied();
    }
    
    public int onEnd() {
        return preconditionsSatisfied ? SUCCESS : FAILURE;
    }
    
    // ----- PROTECTED -----
    
    protected abstract boolean preconditionsSatisfied();
    
    // </editor-fold>  
}
