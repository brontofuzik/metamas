package jadeorg;

import jadeorg.behaviours.NameResponderBehaviour;
import jadeorg.behaviours.PositionAssignmentInitiator;

/**
 *
 * @author Lukáš Kúdela (2011-10-11)
 */
public class Agent extends jade.core.Agent {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private String[] groupPositions;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        initialize();
        System.out.println("Agent { name: " + name + " }");
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
        groupPositions = new String[getArguments().length - 1];
        for (int i = 1; i < getArguments().length; i++) {
            groupPositions[i - 1] = (String)getArguments()[i];
        }
        doInitializeState();
        
        // Postconditions
        assert name != null && !name.isEmpty();
        assert groupPositions != null;
    }
    
    private void initializeBehaviour() {
        // Preconditions
        assert name != null && !name.isEmpty();
        assert groupPositions != null;
        
        addBehaviour(new NameResponderBehaviour(name));
        for (String groupPosition : groupPositions) {
            String groupName = getGroupName(groupPosition);
            String positionName = getPositionName(groupPosition);
            addBehaviour(new PositionAssignmentInitiator(groupName, positionName));
        }
        doInitializeBehaviour();
    }
    
    private String getGroupName(String groupPosition) {
        // Preconditions
        assert groupPosition != null && !groupPosition.isEmpty();
        
        String groupName = groupPosition.substring(0, groupPosition.indexOf('.'));
        
        // Postconditions
        assert groupName != null && !groupName.isEmpty();
        
        return groupName;
    }

    private String getPositionName(String groupPosition) {
        // Preconditions
        assert groupPosition != null && !groupPosition.isEmpty();
        
        String positionName = groupPosition.substring(groupPosition.indexOf('.') + 1);
        
        // Postconditions
        assert positionName != null && !positionName.isEmpty();
        
        return positionName;
    }
    
    // </editor-fold>
}
