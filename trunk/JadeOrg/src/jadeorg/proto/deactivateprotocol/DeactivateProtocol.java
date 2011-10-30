package jadeorg.proto.deactivateprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Deactivate' protocol.
 * DP: Singleton - Singleton
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class DeactivateProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "deactivate-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static DeactivateProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static DeactivateProtocol getInstance() {
        if (singleton == null) {
            singleton = new DeactivateProtocol();
        }
        return singleton;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
    
    // </editor-fold>
}
