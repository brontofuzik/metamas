package jadeorg.player;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jadeorg.proto.Party;

/**
 * A player agent.
 * @author Lukáš Kúdela (2011-10-17)
 */
public class Player extends Agent { 
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private PlayerKnowledgeBase knowledgeBase = new PlayerKnowledgeBase();
    
    // </editor-fold>
    
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

    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private class PlayerManagerBehaviour extends CyclicBehaviour {

        @Override
        public void action() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    private class EnactProtocolInitiator extends Party {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public EnactProtocolInitiator() {
        }
        
        // </editor-fold>
    }
    
    private class DeactProtocolInitiator extends Party {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactProtocolInitiator() {
        }
        
        // </editor-fold>
    
    }
    
    private class ActivateProtocolInitiator extends Party {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public ActivateProtocolInitiator() {
        }
        
        // </editor-fold>
    }
    
    private class DeactivateProtocolInitiator extends Party {
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public DeactivateProtocolInitiator() {
        }
        
        // </editor-fold>      
    }
    
    // </editor-fold>
}
