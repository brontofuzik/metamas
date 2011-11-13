package jadeorg.core.organization;

import jade.core.AID;
import jade.core.behaviours.FSMBehaviour;

/**
 * A power (FSM) behaviour.
 * @author Lukáš Kúdela
 * @since 2011-11-09
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
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return getBehaviourName();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    // </editor-fold>

    void setPlayerAID(AID playerAID) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void setArguments(String arguments) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void makeFSM() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
