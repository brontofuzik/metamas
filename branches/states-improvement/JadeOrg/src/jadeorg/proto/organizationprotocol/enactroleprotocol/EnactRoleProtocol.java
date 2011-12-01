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
   
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "enact-role-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
   
    private static EnactRoleProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private EnactRoleProtocol() {
        super(NAME);
        registerMessages();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static EnactRoleProtocol getInstance() {
        if (singleton == null) {
            singleton = new EnactRoleProtocol();
        }
        return singleton;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void registerMessages() {
        registerMessage(EnactRequestMessage.class);
        registerMessage(RequirementsInformMessage.class);
        registerMessage(RoleAIDMessage.class);
    }
    
    // </editor-fold>
}
