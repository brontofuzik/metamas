package example2.organizations.expressionevaluation.divisor;

import example2.organizations.expressionevaluation.BinaryOperator_Role;

/**
 * The 'Divider' binary operator role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Divider_Role extends BinaryOperator_Role {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /** The name of the 'Divider' role. */
    public static final String NAME = "Divider_Role";
    
    /** The name of the 'Divide' responsibility. */
    public static final String DIVIDE_RESPONSIBILITY = "Divide_Responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the 'Divide' responsibility.
     * @return the name of the 'Divide' responsibility
     */
    @Override
    public String getResponsibilityName() {
        return DIVIDE_RESPONSIBILITY;
    }
    
    // </editor-fold>
}
