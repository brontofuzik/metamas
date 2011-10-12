package jadeorg.behaviours;

import jade.core.behaviours.Behaviour;

/**
 *
 * @author Lukáš Kúdela (2011-10-12)
 */
public class PositionAssignmentInitiator extends Behaviour {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String groupName;
    
    private String positionName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PositionAssignmentInitiator(String groupName, String positionName) {
        assert !groupName.isEmpty();
        assert !positionName.isEmpty();
        
        this.groupName = groupName;
        this.positionName = positionName;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean done() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    // </editor-fold>
}
