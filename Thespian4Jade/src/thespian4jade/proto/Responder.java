package thespian4jade.proto;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import thespian4jade.proto.Protocol;

/**
 * A responder.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
 */
public abstract class Responder extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Responder() {     
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Adds a single responder responding to a given protocol and performative.
     * @param protocol the protocol
     * @param performative the performative
     */
    protected void addResponder(Protocol protocol, int performative) {
        // ----- Preconditions -----
        if (protocol == null) {
            throw new IllegalArgumentException("protocol");
        }
        // -------------------------
        
        ResponderState responder = new ResponderState(protocol, performative);     
        ResponderStateHolder responders = (ResponderStateHolder)getState(ResponderStateHolder.NAME);
        responders.addSubBehaviour(responder);
    }
    
    /**
     * Adds a responder responding to a given protocol and the REQUEST performative.
     * @param protocol the protocol
     */
    protected void addResponder(Protocol protocol) {
        addResponder(protocol, ACLMessage.REQUEST);
    }
    
    /**
     * Builds the responder FSM.
     */
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
    
    /**
     * The 'Responder state holder' (parallel) behaviour. 
     */
    private class ResponderStateHolder extends ParallelBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String NAME = "ResponderStateHolder";
        
        // </editor-fold>
    }
    
    /**
     * The 'Responder' (one-shot) state.
     */
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
        
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
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
        
        // </editor-fold>
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
