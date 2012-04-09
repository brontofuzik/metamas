package thespian4jade.behaviours.states.special;

import thespian4jade.behaviours.states.FSMBehaviourState;
import thespian4jade.behaviours.states.IState;
import thespian4jade.behaviours.states.OneShotBehaviourState;

/**
 * A state that wraps another state (or a whole party), enabling to perform
 * some action before and after the wrapped state is executed.
 * @author Lukáš Kúdela
 * @since 2012-03-15
 * @version %I% %G%
 */
public abstract class WrapperState<TState extends IState>
    extends FSMBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The wrapped state (or party).
     */
    private TState wrappedState;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new insatance of the StateWrapperState class.
     * @param state the state (or party) to be wrapped.
     */
    public WrapperState(TState state) {
        this.wrappedState = state;
        
        buildFSM();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the wrapped state (or party).
     * @return the wrapped state (or party)
     */
    protected TState getWrappedState() {
        return wrappedState;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Override this method to perform some action before the wrapped state
     * (or party) is executed.
     * @param wrappedState the wrapped state (or party)
     */
    protected abstract void doActionBefore(TState wrappedState);
    
    /**
     * Override this method to perform some action after the wrapped state
     * (or party) is executed.
     * @param wrappedState the wrapped state (or party)
     */
    protected abstract void doActionAfter(TState wrappedState);
    
    // ---------- PRIVATE ----------
    
    /**
     * Builds the state FSM.
     */
    private void buildFSM() {
        // ----- States -----
        IState actionBefore = new ActionBefore();
        IState actionAfter = new ActionAfter();
        // ------------------
        
        // Register the states.
        registerFirstState(actionBefore);    
        registerState(wrappedState);     
        registerLastState(actionAfter);
        
        // Register the transitions.
        actionBefore.registerDefaultTransition(wrappedState);     
        wrappedState.registerDefaultTransition(actionAfter);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Action before' (one-shot) state.
     * A state in which the before-action is performed.
     */
    private class ActionBefore extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            doActionBefore(wrappedState);
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Action after' (one-shot) state.
     * A state in which the after-action is performed.
     */
    private class ActionAfter extends OneShotBehaviourState {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        public void action() {
            doActionAfter(wrappedState);
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
