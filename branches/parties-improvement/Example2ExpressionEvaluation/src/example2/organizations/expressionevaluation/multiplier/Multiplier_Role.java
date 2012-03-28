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
    
    public static final String NAME = "Multiplier_Role";
    
    public static final String MULTIPLY_RESPONSIBILITY = "Multiply_Responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public String getResponsibilityName() {
        return MULTIPLY_RESPONSIBILITY;
    }
    
    // </editor-fold>
}
