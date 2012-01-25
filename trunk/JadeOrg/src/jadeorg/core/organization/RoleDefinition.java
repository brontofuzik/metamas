package jadeorg.core.organization;

/**
 * A role definition.
 * A role definition consists of:
 * - the role class,
 * - the role requirements and
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
    private Multiplicity multiplicity;

    /**
     * The role requirements.
     */
    private String[] requirements;

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    RoleDefinition(Class roleClass, Multiplicity multiplicity, String[] requirements) {
        // ----- Preconditions -----
        if (roleClass == null) {
            throw new IllegalArgumentException("roleClass");
        }
        if (requirements == null) {
            throw new IllegalArgumentException("requirements");
        }
        // -------------------------

        this.roleClass = roleClass;
        this.multiplicity = multiplicity;
        this.requirements = requirements;
    }

    RoleDefinition(Class roleClass, Multiplicity multiplicity) {
        this(roleClass, multiplicity, new String[] {});
    }

    RoleDefinition(Class roleClass, String[] requirements) {
        this(roleClass, Multiplicity.SINGLE, requirements);
    }

    RoleDefinition(Class roleClass) {
        this(roleClass, Multiplicity.SINGLE, new String[] {});
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
    Multiplicity getMultiplicity() {
        return multiplicity;
    }

    /**
     * Gets the role requirements.
     * @return the role requirements
     */
    String[] getRequirements() {
        return requirements;
    }

    // </editor-fold>
}
