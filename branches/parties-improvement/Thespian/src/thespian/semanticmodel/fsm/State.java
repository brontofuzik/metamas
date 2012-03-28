package thespian.semanticmodel.fsm;

import java.util.HashMap;
import java.util.Map;
import thespian.semanticmodel.protocol.Message;
import thespian.utilities.Assert;

/**
 * A FSM state.
 * @author Lukáš Kúdela
 * @since 2012-01-13
 * @version %I% %G%
 */
public abstract class State {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private Map<String, Transition> transitions = new HashMap<String, Transition>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public State(String name) {
        Assert.isNotEmpty(name, "name");
        
        this.name = name;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addTransition(Message message, State targetState) {
        Assert.isNotNull(message, "message");
        Assert.isNotNull(targetState, "targetState");
        
        transitions.put(message.getName(), new Transition(this, message, targetState));
    }
    
    // </editor-fold>
}
