package thespian.semanticmodel.organization;

import thespian.utilities.Assert;

/**
 * An organization type.
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class OrganizationType {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public OrganizationType(String name) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------
        
        this.name = name;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public String getName() {
        return name;
    }
    
    // </editor-fold>
   
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addRole(Role roleClass) {
    }
    
    public Organization createOrganization(String name) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        // -------------------------
        
        return new Organization(name, this);
    }
    
    // </editor-fold>
}
