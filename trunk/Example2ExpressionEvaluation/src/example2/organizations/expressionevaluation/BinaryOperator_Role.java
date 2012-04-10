package example2.organizations.expressionevaluation;

import thespian4jade.core.organization.Role;

/**
 * The 'Binary operator' (abstract) role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public abstract class BinaryOperator_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Override this method to return the name of the responsibility.
     * @return the name of the responsibility
     */
    public abstract String getResponsibilityName();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
     
    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new BinaryOperator_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        // No competences.
    }
    
    // </editor-fold>
}
