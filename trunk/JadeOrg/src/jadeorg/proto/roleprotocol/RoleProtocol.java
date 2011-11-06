package jadeorg.proto.roleprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Role' protocol.
 * DP: Singleton - Singleton
 * DP: Abstract factory - Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public class RoleProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "role-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static RoleProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private RoleProtocol() {
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public String getName() {
        return NAME;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public static RoleProtocol getInstance() {
        if (singleton == null) {
            singleton = new RoleProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
}
