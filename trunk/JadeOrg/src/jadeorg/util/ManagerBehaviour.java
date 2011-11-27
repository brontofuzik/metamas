package jadeorg.util;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.FSMBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;

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
        registerStatesAndTransitions();
    }
    
    private void registerStatesAndTransitions() {
        registerFirstState(new ParallelBehaviour(), PARALLEL_BEHAVIOUR_NAME);
        registerState(new BlockerBehaviour(), BLOCKER_BEHAVIOUR_NAME);
        
        registerDefaultTransition(PARALLEL_BEHAVIOUR_NAME, BLOCKER_BEHAVIOUR_NAME);
        registerDefaultTransition(BLOCKER_BEHAVIOUR_NAME, PARALLEL_BEHAVIOUR_NAME,
            new String[] { PARALLEL_BEHAVIOUR_NAME });
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    protected void addHandler(HandlerBehaviour handler) {
        // ----- Preconditions -----
        if (handler == null) {
            throw new IllegalArgumentException("handlerBehaviour");
        }
        // -------------------------
        
        ParallelBehaviour parallelBehaviour = ((ParallelBehaviour)getState(PARALLEL_BEHAVIOUR_NAME));
        parallelBehaviour.addSubBehaviour(handler);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    public abstract class HandlerBehaviour extends OneShotBehaviour {
    }
    
    private class BlockerBehaviour extends OneShotBehaviour {

        @Override
        public void action() {
            getParent().block();
        }
    }
    
    // </editor-fold>
}
