package jadeorg.core;

import jade.core.Agent;
import jadeorg.behaviours.NameResponderBehaviour;
import jadeorg.behaviours._HiringProtocolResponder;

/**
 *
 * @author Lukáš Kúdela (2011-10-10)
 */
public abstract class _Group extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        initialize();
        System.out.println("Group { name: " + name + " }");
    }
    
    // ---------- PROTECTED ----------
    
    // Template method with empty default implementation
    protected /* virtual */ void doInitializeState() {
    }
    
    // Template method with empty default implementation
    protected /* virtual */ void doInitializeBehaviour() {
    }
    
    // ---------- PRIVATE ----------
    
    private void initialize() {
        initializeState();
        initializeBehaviour();
    }
    
    private void initializeState() {
        name = (String)getArguments()[0];
        doInitializeState();
        
        // Postconditions
        assert name != null && !name.isEmpty();
    }
    
    private void initializeBehaviour() {
        // Preconditions
        assert name != null && !name.isEmpty();
        
        addBehaviour(new NameResponderBehaviour(name));
        addBehaviour(new _HiringProtocolResponder());
        doInitializeBehaviour();
    }   
    
    // </editor-fold>
}
