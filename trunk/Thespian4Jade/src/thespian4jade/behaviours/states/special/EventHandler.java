package thespian4jade.behaviours.states.special;

import thespian4jade.behaviours.states.OneShotBehaviourState;

/**
 * An (one-shot) state that handles an event.
 * @author Lukáš Kúdela
 * @since 2012-03-19
 * @version %I% %G%
 */
public abstract class EventHandler<TAgent> extends OneShotBehaviourState {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The event argument.
     */
    private String argument;
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Sets the event argument.
     * @param argument the event argument
     */
    public void setArgument(String argument) {
        this.argument = argument;
    }
    
    // ----- PROTECTED -----
    
    /**
     * Gets the event argument.
     * @return the event argument
     */
    protected String getArgument() {
        return argument;
    }
    
    /**
     * Gets the owner payer.
     * @return the owner player
     */
    protected TAgent getMyPlayer() {
        return (TAgent)myAgent;
    }
    
    // </editor-fold>    

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        handleEvent(argument);
    }
    
    // ----- PROTECTED -----
    
    /**
     * Handles the event.
     * Override this method to handle the event.
     * @param argument the event argument
     */
    protected abstract void handleEvent(String argument);
    
    // </editor-fold>
}
