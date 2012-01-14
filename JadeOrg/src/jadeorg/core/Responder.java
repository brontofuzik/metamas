package jadeorg.core;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.proto.Protocol;

/**
 * A responder.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
 */
public class Responder extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Responder() {     
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected void addResponder(ResponderWrapper responder) {
        // ----- Preconditions -----
        if (responder == null) {
            throw new IllegalArgumentException("responder");
        }
        // -------------------------
        
        ParallelBehaviour parallelBehaviour = ((ParallelBehaviour)getState(ParallelBehaviour.NAME));
        parallelBehaviour.addSubBehaviour(responder);
    }
    
    // ----- PRIVATE -----
    
    private void buildFSM() {
        // Register the states.
        registerFirstState(new ParallelBehaviour(), ParallelBehaviour.NAME);
        registerState(new BlockerBehaviour(), BlockerBehaviour.NAME);
        
        // Register the transitions.
        registerDefaultTransition(ParallelBehaviour.NAME, BlockerBehaviour.NAME);
        registerDefaultTransition(BlockerBehaviour.NAME, ParallelBehaviour.NAME,
            new String[] { ParallelBehaviour.NAME });
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected abstract class ResponderWrapper extends OneShotBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private Protocol protocol;
        
        private int performative;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected ResponderWrapper(Protocol protocol, int performative) {
            // ----- Preconditions -----
            assert protocol != null;
            assert performative >= 0;
            // -------------------------
            
            this.protocol = protocol;
            this.performative = performative;
        }
        
        protected ResponderWrapper(Protocol protocol) {
            this(protocol, ACLMessage.REQUEST);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                protocol.getTemplate(),
                MessageTemplate.MatchPerformative(performative));
                 
            ACLMessage message = myAgent.receive(template);          
            if (message != null) {
                handleMessage(message);
            }
        }
        
        // ----- PROTECTED -----
        
        protected abstract void handleMessage(ACLMessage message);
        
        // </editor-fold>
    }
    
    private class ParallelBehaviour extends jade.core.behaviours.ParallelBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "ParallelBehaviour";
        
        // </editor-fold>
    }
    
    private class BlockerBehaviour extends OneShotBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "BlockerBehaviour";
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            if (myAgent.getCurQueueSize() == 0) {
                getParent().block();
            }
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
