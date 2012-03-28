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
    
    public static final String NAME = "Adder_Role";

    public static final String ADD_RESPONSIBILITY = "Add_Responsibility";
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    @Override
    public String getResponsibilityName() {
        return ADD_RESPONSIBILITY;
    }

    // </editor-fold>
}
