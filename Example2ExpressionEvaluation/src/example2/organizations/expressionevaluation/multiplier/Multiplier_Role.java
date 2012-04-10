package example2.organizations.expressionevaluation.multiplier;

import example2.organizations.expressionevaluation.BinaryOperator_Role;

/**
 * The 'Multiplier' binary operator role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Multiplier_Role extends BinaryOperator_Role {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /** The name of the 'Multiplier' role. */
    public static final String NAME = "Multiplier_Role";
    
    /** The name of the 'Multiply' responsibility. */
    public static final String MULTIPLY_RESPONSIBILITY = "Multiply_Responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the 'Multiply' responsibility.
     * @return the name of the 'Multiply' responsibility
     */
    @Override
    public String getResponsibilityName() {
        return MULTIPLY_RESPONSIBILITY;
    }
    
    // </editor-fold>
}
