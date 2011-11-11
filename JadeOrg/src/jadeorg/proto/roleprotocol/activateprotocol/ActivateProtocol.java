package jadeorg.proto.roleprotocol.activateprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Activate' protocol.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class ActivateProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constant field">
    
    private static final String NAME = "activate-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static ActivateProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static ActivateProtocol getInstance() {
        if (singleton == null) {
            singleton = new ActivateProtocol();
        }
        return singleton;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    // </editor-fold>
}
