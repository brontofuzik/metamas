package jadeorg.core.organization;

import jadeorg.proto.jadeextensions.OneShotBehaviourState;

/**
 * An 'Error handler' state.
 * @author Lukáš Kúdela
 * @since 2011-11-13
 * @version %I% %G%
 */
class ErrorHandler extends OneShotBehaviourState {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
        
    ErrorHandler(String name) {
        super(name);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public Power getMyPower() {
        return (Power)getParent();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    public void action() {
//        final int FAILURE = 2;
//        
//        setExitValue(FAILURE);
    }
    
    // </editor-fold>
}
