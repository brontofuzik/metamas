package example2.organizations.expressionevaluation.adder;

import example2.organizations.expressionevaluation.BinaryOperator_Role;

/**
 * The 'Adder' binary operator role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Adder_Role extends BinaryOperator_Role {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /** The name of the 'Adder' role. */
    public static final String NAME = "Adder_Role";

    /** The name of the 'Add' responsibility. */
    public static final String ADD_RESPONSIBILITY = "Add_Responsibility";
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the 'Add' responsibility.
     * @return the name of the 'Add' responsibility
     */
    @Override
    public String getResponsibilityName() {
        return ADD_RESPONSIBILITY;
    }

    // </editor-fold>
}
