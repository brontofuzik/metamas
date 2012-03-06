package thespian4jade.core;

import jade.core.Agent;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import thespian4jade.proto.Protocol;

/**
 * An initiator.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
 */
public abstract class Initiator extends FSMBehaviour {
    
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
        setProtocol(protocol);
        setArguments(arguments);
        reset();
        
        myAgent.addBehaviour(this);
    }
    
    // ----- PROTECTED -----
    
    protected void addInitiator(Protocol protocol) {
        // ----- Preconditions -----
        if (protocol == null) {
            throw new IllegalArgumentException("protocol");
        }
        // -------------------------
        
        InitiatorState initiator = new InitiatorState(protocol);
        registerLastState(initiator, initiator.getProtocol().getName());
        registerTransition(SelectInitiator.NAME, initiator.getProtocol().getName(),
            initiator.getProtocol().getName().hashCode());
    }
    
    // ----- PRIVATE -----
    
    private void buildFSM() {
        // Register the states.
        registerFirstState(new SelectInitiator(), SelectInitiator.NAME);
        
        // Register the transitions.
        // No transitions.
    }
    
    private Protocol getProtocol() {
        SelectInitiator selectInitiator = (SelectInitiator)getState(SelectInitiator.NAME);
        return selectInitiator.getProtocol();
    }
    
    private void setProtocol(Protocol protocol) {
        SelectInitiator selectInitiator = (SelectInitiator)getState(SelectInitiator.NAME);
        selectInitiator.setProtocol(protocol);
    }
    
    private void setArguments(Object[] arguments) {
        InitiatorState initiatorState = (InitiatorState)getState(getProtocol().getName());
        initiatorState.setArguments(arguments);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    // ----- PRIVATE -----
    
    private class SelectInitiator extends OneShotBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "SelectInitiator"; 
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private Protocol protocol;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        private Protocol getProtocol() {
            return protocol;
        }
        
        private void setProtocol(Protocol protocol) {
            this.protocol = protocol;
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            // Do nothing.
        }
        
        public int onEnd() {
            return protocol.getName().hashCode();
        }
                
        // </editor-fold>
    }
    
    private class InitiatorState extends OneShotBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private Protocol protocol;
        
        private Object[] arguments;
               
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        private InitiatorState(Protocol protocol) {
            // ----- Preconditions -----
            assert protocol != null;
            // -------------------------
            
            this.protocol = protocol;
        }
            
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        private Protocol getProtocol() {
            return this.protocol;
        }
        
        private void setArguments(Object[] arguments) {
            this.arguments = arguments;
        }
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            myAgent.addBehaviour(protocol.createInitiatorParty(arguments));
        }
        
         // </editor-fold>     
    }
    
    // </editor-fold>
}
