package metamas.semanticmodel;

import metamas.semanticmodel.AgentClass;
import metamas.utilities.Assert;

/**
 * An concrete agent - an instance of an agent class.
 * @author Lukáš Kúdela
 */
public class Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;

    private AgentClass klass;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Agent(String name, AgentClass klass) {
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

    public AgentClass getKlass() {
        return klass;
    }

    // </editor-fold>
}
