package jadeorg.core.organization;

import jadeorg.proto.State;

/**
 * An 'Error handler' state.
 * @author Lukáš Kúdela
 * @since 2011-11-13
 * @version %I% %G%
 */
class ErrorHandler extends State {
    
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
        setExitValue(Event.FAILURE);
    }
    
    // </editor-fold>
}
