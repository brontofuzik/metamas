package jadeorg.player;

import jade.core.AID;

/**
 * A role description.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
class RoleDescription {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The role AID. */
    private AID roleAID;
    
    /** The name of the role */
    private String roleName;
    
    /** The organization AID. */
    private AID organizationAID;
    
    /** The name of the organization */
    private String organizationName;

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RoleDescription(AID roleAID, AID organizationAID) {
        this.roleAID = roleAID;
        roleName = roleAID.getName();
        
        this.organizationAID = organizationAID;
        organizationName = organizationAID.getName();       
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public AID getOrganizationAID() {
        return organizationAID;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public AID getRoleAID() {
        return roleAID;
    }

    public String getRoleName() {
        return roleName;
    }
    
    // </editor-fold>
}
