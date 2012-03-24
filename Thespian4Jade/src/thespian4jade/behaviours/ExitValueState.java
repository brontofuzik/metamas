package thespian4jade.behaviours;

import thespian4jade.behaviours.jadeextensions.OneShotBehaviourState;

/**
 * The 'Exit value' state.
 * @author Lukáš Kúdela
 * @since 2012-01-22
 * @version %I% %G%
 */
public abstract class ExitValueState extends OneShotBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The exit value.
     */
    private int exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action () {
        exitValue = doAction();
    }
    
    @Override
    public int onEnd() {
        return exitValue;
    }
    
    // ----- PROTECTED -----
    
    /**
     * Performs action.
     * Design pattern: Template method - Primitive operation
     * @return the exit value
     */
    protected abstract int doAction();
    
    // </editor-fold>
}
