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
    
    /** The name of the 'Subtractor' role. */
    public static final String NAME = "Subtractor_Role";
    
    /** The name of the 'Subtract' responsibility. */
    public static final String SUBTRACT_RESPONSIBILITY = "Subtract_Responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the 'Subtract' responsibility.
     * @return the name of the 'Subtract' responsibility
     */
    @Override
    public String getResponsibilityName() {
        return SUBTRACT_RESPONSIBILITY;
    }
    
    // </editor-fold>
}
