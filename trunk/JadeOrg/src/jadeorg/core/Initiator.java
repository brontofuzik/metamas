package jadeorg.core;

import jade.core.Agent;
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
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected Initiator(Agent agent) {
        // ----- Preconditions -----
        assert agent != null;
        // -------------------------
        
        myAgent = agent;
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void initiateProtocol(Protocol protocol, Object[] arguments) {
        InitiatorWrapper initiator = (InitiatorWrapper)getState(protocol.getName());
        initiator.setArguments(arguments);   
        registerDefaultTransition(SelectInitiator.NAME, protocol.getName());
        reset();
        
        myAgent.addBehaviour(this);
    }
    
    // ----- PROTECTED -----
    
    protected void addInitiator(InitiatorWrapper initiator) {
        // ----- Preconditions -----
        if (initiator == null) {
            throw new IllegalArgumentException("initiator");
        }
        // -------------------------
        
        // Register as one of the final states.
        registerLastState(initiator, initiator.getName());
    }
    
    // ----- PRIVATE -----
    
    private void buildFSM() {
        // Register the initial state.
        registerFirstState(new SelectInitiator(), SelectInitiator.NAME);
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
        
        public String getName() {
            return protocol.getName();
        }
        
        public abstract void setArguments(Object[] arguments);
        
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
                
        // </editor-fold>
    }
    
    // </editor-fold>
}
