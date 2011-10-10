package metamas.semanticmodel;

import metamas.utilities.Assert;

/**
 *
 * @author Lukáš Kúdela
 */
public class Skill {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Skill(String name) {
        Assert.isNotEmpty(name, "name");
        
        this.name = name;
    }
        
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">
    
    public String getName() {
        return name;
    }
    
    // </editor-fold>
}
