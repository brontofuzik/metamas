package jadeorg.proto.organizationprotocol.deactroleprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Deact' protocol.
 * DP: Singleton - Singleton
 * DP: Abstract factory - Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-10-24
 * @version %I% %G%
 */
public class DeactRoleProtocol extends Protocol {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "deact-role-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static DeactRoleProtocol singleton;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private DeactRoleProtocol() {
        super(NAME);
        registerMessages();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static DeactRoleProtocol getInstance() {
        if (singleton == null) {
            singleton = new DeactRoleProtocol();
        }
        return singleton;              
    }
        
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    private void registerMessages() {
        registerMessage(DeactRequestMessage.class);
        registerMessage(FailureMessage.class);
    }
    
    // </editor-fold>
}
