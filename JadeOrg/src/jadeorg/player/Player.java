package jadeorg.player;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.FSMBehaviour;

/**
 * A player agent.
 * @author Lukáš Kúdela (2011-10-17)
 */
public class Player extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void setup() {
        initialize();
    }
    
    // ---------- PRIVATE ----------
    
    private void initialize() {
        initializeState();
        initializeBehaviour();
    }

    private void initializeState() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void initializeBehaviour() {
        addBehaviour(new PlayerManagerBehaviour());
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private class PlayerManagerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    private class EnactProtocolInitiator extends FSMBehaviour {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public EnactProtocolInitiator() {
        }
        
        // </editor-fold>
    }
    
    private class DeactProtocolInitiator extends FSMBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactProtocolInitiator() {
        }
        
        // </editor-fold>
    
    }
    
    private class ActivateProtocolInitiator extends FSMBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public ActivateProtocolInitiator() {
        }
        
        // </editor-fold>
    }
    
    private class DeactivateProtocolInitiator extends FSMBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactivateProtocolInitiator() {
        }
        
        // </editor-fold>      
    }
    
    // </editor-fold>
}
