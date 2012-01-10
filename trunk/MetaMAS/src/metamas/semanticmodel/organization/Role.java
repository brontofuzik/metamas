package metamas.semanticmodel.organization;

import metamas.utilities.Assert;

/**
 *
 * @author Lukáš Kúdela
 */
public class Role {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;

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

    // </editor-fold>
}
