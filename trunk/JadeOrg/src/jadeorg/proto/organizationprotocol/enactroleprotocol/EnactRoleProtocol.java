package jadeorg.proto.organizationprotocol.enactroleprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Enact' protocol.
 * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
 * DP: This cass plays the role of 'Concrete factory' in the Abstract factory design pattern.
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public class EnactRoleProtocol extends Protocol {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
   
    private static EnactRoleProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static EnactRoleProtocol getInstance() {
        if (singleton == null) {
            singleton = new EnactRoleProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
}
