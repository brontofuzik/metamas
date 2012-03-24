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
    
    public static final String NAME = "Divider_Role";
    
    public static final String DIVIDE_RESPONSIBILITY = "Divide_Responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public String getResponsibilityName() {
        return DIVIDE_RESPONSIBILITY;
    }
    
    // </editor-fold>
}
