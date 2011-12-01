package jadeorg.proto.roleprotocol.activateprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Activate' protocol.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class ActivateRoleProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constant field">
    
    private static final String NAME = "activate-role-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static ActivateRoleProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private ActivateRoleProtocol() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static ActivateRoleProtocol getInstance() {
        if (singleton == null) {
            singleton = new ActivateRoleProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
}
