package jadeorg.proto.roleprotocol.deactivateroleprotocol;

import jadeorg.proto_new.Protocol;

/**
 * The 'Deactivate' protocol.
 * DP: Singleton - Singleton
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class DeactivateRoleProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "deactivate-role-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static DeactivateRoleProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private DeactivateRoleProtocol() {
        super(NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public static DeactivateRoleProtocol getInstance() {
        if (singleton == null) {
            singleton = new DeactivateRoleProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
}
