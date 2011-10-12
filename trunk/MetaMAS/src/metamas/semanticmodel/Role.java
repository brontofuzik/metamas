package metamas.semanticmodel;

import metamas.semanticmodel.Skill;
import java.util.ArrayList;
import java.util.List;
import metamas.utilities.Assert;

/**
 *
 * @author Lukáš Kúdela
 */
public class Role {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;
    
    private List<Skill> requiredSkills = new ArrayList<Skill>();

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Role(String name) {
        Assert.isNotEmpty(name, "name");

        this.name = name;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return name;
    }
    
    public Iterable<Skill> getRequiredSkills() {
        return requiredSkills;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public boolean requiresSkill(Skill skill) {
        Assert.isNotNull(skill, "skill");
        
        return requiredSkills.contains(skill);
    }
    
    public void addRequiredSkill(Skill skill) {
        Assert.isNotNull(skill, "skill");
        
        requiredSkills.add(skill);
    }
    
    // </editor-fold>
}
