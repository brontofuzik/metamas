package jadeorg.util;

import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jadeorg.proto.Protocol;

/**
 * A manager behaviour.
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public abstract class ManagerBehaviour extends FSMBehaviour {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String PARALLEL_BEHAVIOUR_NAME = "parallel";
    
    private static final String BLOCKER_BEHAVIOUR_NAME = "blocker";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public ManagerBehaviour() {     
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected void addHandler(HandlerBehaviour handler) {
        // ----- Preconditions -----
        if (handler == null) {
            throw new IllegalArgumentException("handler");
        }
        // -------------------------
        
        ParallelBehaviour parallelBehaviour = ((ParallelBehaviour)getState(PARALLEL_BEHAVIOUR_NAME));
        parallelBehaviour.addSubBehaviour(handler);
    }
    
    private void buildFSM() {
        registerFirstState(new ParallelBehaviour(), PARALLEL_BEHAVIOUR_NAME);
        registerState(new BlockerBehaviour(), BLOCKER_BEHAVIOUR_NAME);
        
        registerDefaultTransition(PARALLEL_BEHAVIOUR_NAME, BLOCKER_BEHAVIOUR_NAME);
        registerDefaultTransition(BLOCKER_BEHAVIOUR_NAME, PARALLEL_BEHAVIOUR_NAME,
            new String[] { PARALLEL_BEHAVIOUR_NAME });
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected abstract class HandlerBehaviour extends OneShotBehaviour {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private Protocol protocol;
        
        private int performative;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected HandlerBehaviour(Protocol protocol, int performative) {
            // ----- Preconditions -----
            assert protocol != null;
            assert performative >= 0;
            // -------------------------
            
            this.protocol = protocol;
            this.performative = performative;
        }
        
        protected HandlerBehaviour(Protocol protocol) {
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
    
    private class BlockerBehaviour extends OneShotBehaviour {

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
