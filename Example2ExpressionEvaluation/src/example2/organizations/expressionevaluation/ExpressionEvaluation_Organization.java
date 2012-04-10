package example2.organizations.expressionevaluation;

import example2.organizations.expressionevaluation.adder.Adder_Role;
import example2.organizations.expressionevaluation.divisor.Divider_Role;
import example2.organizations.expressionevaluation.evaluator.Evaluator_Role;
import example2.organizations.expressionevaluation.multiplier.Multiplier_Role;
import example2.organizations.expressionevaluation.subtractor.Subtractor_Role;
import thespian4jade.core.organization.Organization;

/**
 * The 'Expression evaluation' organization.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class ExpressionEvaluation_Organization extends Organization {
 
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add roles.
        addRole(Evaluator_Role.class);
        addRole(Adder_Role.class, new String[] { "Add_Responsibility" });
        addRole(Subtractor_Role.class, new String[] { "Subtract_Responsibility" });
        addRole(Multiplier_Role.class, new String[] { "Multiply_Responsibility" });
        addRole(Divider_Role.class, new String[] { "Divide_Responsibility" });
    }   
    
    // </editor-fold> 
}
