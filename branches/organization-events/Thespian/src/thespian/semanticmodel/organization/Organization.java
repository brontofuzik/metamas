package thespian.semanticmodel.organization;

import thespian.utilities.Assert;

/**
 * A concrete organization - an instance of an organization class.
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class Organization {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private OrganizationType type;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Organization(String name, OrganizationType klass) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        Assert.isNotNull(klass, "klass");
        // -------------------------
        
        this.name = name;
        this.type = klass;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public String getName() {
        return name;
    }

    public OrganizationType getType() {
        return type;
    }   
    
    // </editor-fold>
}
