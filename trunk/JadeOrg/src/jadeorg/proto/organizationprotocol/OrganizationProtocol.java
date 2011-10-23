package jadeorg.proto.organizationprotocol;

import jadeorg.lang.Protocol;

/**
 * The 'Organization' protocol.
 * DP: This class plays the role of 'Singleton' in the Singleton design pattern.
 * DP: This cass plays the role of 'Concrete factory' in the Abstract factory design pattern.
 * @author Lukáš Kúdela (2011-10-21)
 * @version 0.1
 */
public class OrganizationProtocol extends Protocol {
   
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static OrganizationProtocol singleton;
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    private OrganizationProtocol() {
        super();
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
    
    @Override
    public void registerMessages() {
        registerMessage(new OrganizationMessage());
    }
    
    // </editor-fold>
}
