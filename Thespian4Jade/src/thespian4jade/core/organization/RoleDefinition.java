package thespian4jade.core.organization;

/**
 * A role definition.
 * A role definition consists of:
 * - the role class,
 * - the role responsibilites and
 * - the role multiplicity.
 */
class RoleDefinition {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /**
     * The role class.
     */
    private Class roleClass;

    /**
     * The role multiplicity.
     */
    private RoleMultiplicity multiplicity;

    /**
     * The role responsibilites.
     */
    private String[] responsibilites;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    RoleDefinition(Class roleClass, RoleMultiplicity multiplicity, String[] responsibilites) {
        // ----- Preconditions -----
        if (roleClass == null) {
            throw new IllegalArgumentException("roleClass");
        }
        if (responsibilites == null) {
            throw new IllegalArgumentException("responsibilities");
        }
        // -------------------------

        this.roleClass = roleClass;
        this.multiplicity = multiplicity;
        this.responsibilites = responsibilites;
    }

    RoleDefinition(Class roleClass, RoleMultiplicity multiplicity) {
        this(roleClass, multiplicity, new String[] {});
    }

    RoleDefinition(Class roleClass, String[] responsibilities) {
        this(roleClass, RoleMultiplicity.SINGLE, responsibilities);
    }

    RoleDefinition(Class roleClass) {
        this(roleClass, RoleMultiplicity.SINGLE, new String[] {});
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">

    /**
     * Gets the name of the role.
     * @return the name of the role
     */
    String getName() {
        return roleClass.getSimpleName();
    }

    /**
     * Gets the role class.
     * @return the role class
     */
    Class getRoleClass() {
        return roleClass;
    }

    /**
     * Gets the role multiplicity
     * @return the role multiplicity
     */
    RoleMultiplicity getMultiplicity() {
        return multiplicity;
    }

    /**
     * Gets the role responsibilities.
     * @return the role responsibilities
     */
    String[] getResponsibilities() {
        return responsibilites;
    }

    // </editor-fold>
}
