package metamas.semanticmodel.interactionprotocol;

import metamas.utilities.Assert;

/**
 * An interaction protocol transition.
 * @author Lukáš Kúdela
 */
class Transition {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private State sourceState;
    
    private Message message;
    
    private State targetState;
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    Transition(State sourceState, Message message, State targetState) {
        Assert.isNotNull(sourceState, "sourceState");
        Assert.isNotNull(message, "message");
        Assert.isNotNull(targetState, "targetState");
        
        this.sourceState = sourceState;
        this.message = message;
        this.targetState = targetState;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public Message getMessage() {
        return message;
    }

    public State getSourceState() {
        return sourceState;
    }

    public State getTargetState() {
        return targetState;
    }
    
    // </editor-fold>
}
