package metamas.semanticmodel;

import metamas.semanticmodel.OrganizationClass;
import metamas.utilities.Assert;

/**
 * A concrete organization - an instance of an organization class.
 * @author hp
 */
public class Organization {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private OrganizationClass klass;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Organization(String name, OrganizationClass klass) {
        Assert.isNotEmpty(name, "name");
        Assert.isNotNull(klass, "klass");
        
        this.name = name;
        this.klass = klass;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public String getName() {
        return name;
    }

    public OrganizationClass getKlass() {
        return klass;
    }   
    
    // </editor-fold>
}
