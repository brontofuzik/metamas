package jadeorg.core.organization;

import jade.core.behaviours.FSMBehaviour;

/**
 * A 'Meet requirement initiator' (FSM) behaviour.
 * @author Lukáš Kúdela
 * @since 2011-11-13
 * @version %I% %G%
 */
public class MeetRequirementInitiator extends FSMBehaviour {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Object argument;
    
    private Object result;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    MeetRequirementInitiator(String name) {
        setBehaviourName(name);
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    String getName() {
        return getBehaviourName();
    }
    
    Power getMyPower() {
        return (Power)getParent();
    }
    
    void setArgument(Object argument) {
        this.argument = argument;
    }
    
    Object getResult() {
        return result;
    }
    
    // </editor-fold>   
}
