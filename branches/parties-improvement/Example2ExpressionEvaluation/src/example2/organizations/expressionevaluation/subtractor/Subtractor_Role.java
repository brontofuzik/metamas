package example2.organizations.expressionevaluation.subtractor;

import example2.organizations.expressionevaluation.BinaryOperator_Role;

/**
 * The 'Subtractor' binary operator role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Subtractor_Role extends BinaryOperator_Role {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    public static final String NAME = "Subtractor_Role";
    
    public static final String SUBTRACT_RESPONSIBILITY = "Subtract_Responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public String getResponsibilityName() {
        return SUBTRACT_RESPONSIBILITY;
    }
    
    // </editor-fold>
}
