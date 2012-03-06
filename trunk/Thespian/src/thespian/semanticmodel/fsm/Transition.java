package thespian.semanticmodel.fsm;

import thespian.semanticmodel.protocol.Message;
import thespian.utilities.Assert;

/**
 * A FSM transition.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
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
