package thespian4jade.core.player;

import thespian4jade.protocols.jadeextensions.OneShotBehaviourState;

/**
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
    
    protected abstract void handleEvent(String argument);
    
    // </editor-fold>
}
