package jadeorg.proto;

import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * The 'Initialize' state.
 * @author Lukáš Kúdela
 * @since 2012-01-22
 * @version %I% %G%
 */
public abstract class Initialize extends OneShotBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    public static final int OK = 0;
    public static final int FAIL = 1;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private int exitValue;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action () {
        exitValue = initialize();
    }
    
    @Override
    public int onEnd() {
        return exitValue;
    }
    
    // ----- PROTECTED -----
    
    protected abstract int initialize();
    
    // </editor-fold>
}
