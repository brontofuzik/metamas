package jadeorg.core;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
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
    
    protected void addResponder(Protocol protocol) {
        // ----- Preconditions -----
        if (protocol == null) {
            throw new IllegalArgumentException("protocol");
        }
        // -------------------------
        
        ResponderState responder = new ResponderState(protocol);     
        ResponderStateHolder responders = (ResponderStateHolder)getState(ResponderStateHolder.NAME);
        responders.addSubBehaviour(responder);
    }
    
    // ----- PRIVATE -----
    
    private void buildFSM() {
        // Register the states.
        registerFirstState(new ResponderStateHolder(), ResponderStateHolder.NAME);
        registerState(new BlockerState(), BlockerState.NAME);
        
        // Register the transitions.
        registerDefaultTransition(ResponderStateHolder.NAME, BlockerState.NAME);
        registerDefaultTransition(BlockerState.NAME, ResponderStateHolder.NAME,
            new String[] { ResponderStateHolder.NAME });
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
       
    private class ResponderStateHolder extends ParallelBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "ResponderStateHolder";
        
        // </editor-fold>
    }
    
    private class ResponderState extends OneShotBehaviour {

        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private Protocol protocol;
        
        private int performative;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        private ResponderState(Protocol protocol, int performative) {
            // ----- Preconditions -----
            assert protocol != null;
            assert performative >= 0;
            // -------------------------
            
            this.protocol = protocol;
            this.performative = performative;
        }
        
        private ResponderState(Protocol protocol) {
            this(protocol, ACLMessage.REQUEST);
        }
        
        // </editor-fold>

        @Override
        public void action() {
            MessageTemplate template = MessageTemplate.and(
                protocol.getTemplate(),
                MessageTemplate.MatchPerformative(performative));
                 
            ACLMessage message = myAgent.receive(template);          
            if (message != null) {
                myAgent.addBehaviour(protocol.createResponderParty(message));
            }
        }
    }
    
    /**
     * The 'Blocker' (one-shot) state.
     */
    private class BlockerState extends OneShotBehaviour {

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
