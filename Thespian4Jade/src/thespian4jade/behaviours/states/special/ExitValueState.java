package thespian4jade.behaviours.states.special;

import thespian4jade.behaviours.states.OneShotBehaviourState;

/**
 * A (one-shot) state that yields an exit value after its execution.
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
     * Performs the stte action.
     * Override this method to return the exit value.
     * Design pattern: Template method - Primitive operation
     * @return the exit value the exit value
     */
    protected abstract int doAction();
    
    // </editor-fold>
}
