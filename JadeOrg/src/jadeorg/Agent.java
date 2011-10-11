package jadeorg;

/**
 *
 * @author Lukáš Kúdela (2011-10-11)
 */
public class Agent extends jade.core.Agent {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private String[] groupNames;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        initialize();
        System.out.println("Agent name: " + name);
    }
    
    // ---------- PRIVATE ----------

    private void initialize() {
        name = (String)getArguments()[0];
        groupNames = new String[getArguments().length - 1];
        for (int i = 1; i < getArguments().length; i++) {
            groupNames[i - 1] = (String)getArguments()[i];
        }
    }
    
    // </editor-fold>
}
