package metamas.semanticmodel;

import metamas.semanticmodel.Skill;
import java.util.ArrayList;
import java.util.List;
import metamas.utilities.Assert;

/**
 *
 * @author Lukáš Kúdela
 */
public class AgentClass {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;
    
    private List<Skill> posessedSkills = new ArrayList<Skill>();

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public AgentClass(String name) {
        Assert.isNotEmpty(name, "name");

        this.name = name;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return name;
    }
    
    public Iterable<Skill> getPosessedSkills() {
        return posessedSkills;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public boolean possessesSkill(Skill skill) {
        Assert.isNotNull(skill, "skill");
        
        return posessedSkills.contains(skill);
    }
    
    public void addPosessedSkill(Skill skill) {
        Assert.isNotNull(skill, "skill");
        
        posessedSkills.add(skill);
    }

    public Agent createAgent(String name) {
        Assert.isNotEmpty(name, "name");

        return new Agent(name, this);
    }

    // </editor-fold>
}
