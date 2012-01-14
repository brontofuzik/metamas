package jadeorg.core;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jadeorg.proto.Protocol;

/**
 * An initiator.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
 */
public class Initiator extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Protocol protocol;
    
    private Object[] arguments; 
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Initiator() {
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    protected Object[] getArguments() {
        return arguments;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void initiateProtocol(Protocol protocol, Object[] arguments) {
        this.protocol = protocol;
        this.arguments = arguments;
    }
    
    // ----- PROTECTED -----
    
    protected void addInitiator(InitiatorWrapper initiator) {
        // ----- Preconditions -----
        if (initiator == null) {
            throw new IllegalArgumentException("initiator");
        }
        // -------------------------
        
        // Register the initiator-related state.
        registerLastState(initiator, initiator.getBehaviourName());
        
        // Register the initiator-related transition.
        registerTransition(SelectInitiator.NAME, initiator.getBehaviourName(),
                initiator.getProtocol().hashCode());
    }
    
    // ----- PRIVATE -----
    
    private void buildFSM() {
        // Register the states.
        registerFirstState(new SelectInitiator(), SelectInitiator.NAME);
        
        // Register the transitions.
        // No transitions.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected abstract class InitiatorWrapper extends OneShotBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private Protocol protocol;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected InitiatorWrapper(Protocol protocol) {
            // ----- Preconditions -----
            assert protocol != null;
            // -------------------------
            
            this.protocol = protocol;
        }
            
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        public Protocol getProtocol() {
            return protocol;
        }
        
        // </editor-fold>
    }
    
    // ----- PRIVATE -----
    
    private class SelectInitiator extends OneShotBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "SelectInitiator"; 
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }
        
        public int onEnd() {
            return protocol.hashCode();
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
