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
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static OrganizationProtocol singleton;
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private OrganizationProtocol() {
        registerMessages();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public String getName() {
        return "organization-protocol";
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public static OrganizationProtocol getInstance() {
        if (singleton == null) {
            singleton = new OrganizationProtocol();
        }
        return singleton;
    }
    
    // ---------- PRIVATE ----------
    
    private void registerMessages() {
        registerMessage(new OrganizationMessage());
    }
    
    // </editor-fold>
}
