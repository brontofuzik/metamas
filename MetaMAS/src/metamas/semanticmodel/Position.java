package metamas.semanticmodel;

import metamas.utilities.Assert;

/**
 * A position within a group typed to a role.
 * @author Lukáš Kúdela
 */
public abstract class Position {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The name of the position. */
    protected String name;

    /** The role of the position. */
    protected Role role;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Position(String name, Role role) {
        Assert.isNotEmpty(name, "name");
        Assert.isNotNull(role, "role");

        this.name  = name;
        this.role = role;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return name;
    }
    
    public Role getRole() {
        return role;
    }

    // </editor-fold>
}
