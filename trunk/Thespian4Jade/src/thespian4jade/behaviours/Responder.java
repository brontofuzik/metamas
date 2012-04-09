package thespian4jade.behaviours;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import thespian4jade.protocols.Protocol;

/**
 * A responder behaviour that receives protocol initiation messages and
 * invokes the corresponding protocol responder parties.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
 */
public abstract class Responder extends FSMBehaviour {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Responder class.
     */
    public Responder() {     
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Adds a protocol to which the responder should respond.
     * @param protocol the protocol to which the responder should respond
     */
    protected void addProtocol(Protocol protocol) {
        // ----- Preconditions -----
        if (protocol == null) {
            throw new IllegalArgumentException("protocol");
        }
        // -------------------------
        
        ResponderState responder = new ResponderState(protocol, protocol.getPerformative());     
        ResponderStateHolder responders = (ResponderStateHolder)getState(ResponderStateHolder.NAME);
        responders.addSubBehaviour(responder);
    }
    
    // ----- PRIVATE -----
    
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
