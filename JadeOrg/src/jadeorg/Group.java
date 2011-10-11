package jadeorg;

import jade.core.Agent;

/**
 *
 * @author Lukáš Kúdela (2011-10-10)
 */
public class Group extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        initialize();
        System.out.println("Group name: " + name);
    }
    
    // ---------- PRIVATE ----------
    
    private void initialize() {
        name = (String)getArguments()[0];
    }
    
    // </editor-fold>
}
