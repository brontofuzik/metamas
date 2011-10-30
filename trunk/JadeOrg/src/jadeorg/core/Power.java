package jadeorg.core;

import jadeorg.proto.State;
import jade.core.behaviours.FSMBehaviour;

/**
 * A power.
 * @author Lukáš Kúdela
 * @since 2011-11-19
 * @version %I% %G%
 */
public class Power extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String LAST_STATE_NAME = "last-state";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Role myRole;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Power(String name) {
        // ----- Preconditions -----
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("name");
        }
        // -------------------------
        
        setBehaviourName(name);
        //State lastState = new State(LAST_STATE_NAME);
        //registerLastState(lastState, LAST_STATE_NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    // </editor-fold>
}
