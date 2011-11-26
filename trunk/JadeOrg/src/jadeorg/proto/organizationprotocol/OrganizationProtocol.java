package jadeorg.proto.organizationprotocol;

import jadeorg.proto.Protocol;

/**
 * The 'Organization' protocol.
 * DP: Singleton - Singleton
 * DP: Abstract factory - Concrete factory
 * @author Lukáš Kúdela
 * @since 2011-10-21
 * @version %I% %G%
 */
public class OrganizationProtocol extends Protocol {
   
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String NAME = "organization-protocol";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static OrganizationProtocol singleton;
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private OrganizationProtocol() {
        super(NAME);
        
        registerMessages();
        setParentProtocol(null);
    }
    
    private void registerMessages() {
        registerMessage(EnactRequestMessage.class);
        registerMessage(DeactRequestMessage.class);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public static OrganizationProtocol getInstance() {
        if (singleton == null) {
            singleton = new OrganizationProtocol();
        }
        return singleton;
    }
        
    // </editor-fold>
}
