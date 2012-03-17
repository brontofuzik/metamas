package example2.organizations.expressionevaluation.divisor;

import thespian4jade.core.organization.Role;

/**
 * The 'Divider' role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Divider_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    public static final String NAME = "Divider_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the Divider_Role class.
     */
    static {
        addRequirement("Divide_Responsibility");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Divider_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        // No competences.
    }

    // </editor-fold>
}
