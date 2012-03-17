package example2.organizations.expressionevaluation.multiplier;

import thespian4jade.core.organization.Role;

/**
 * The 'Multiplier' role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Multiplier_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    public static final String NAME = "Multiplier_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the Multiplier_Role class.
     */
    static {
        addRequirement("Multiply_Responsibility");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Multiplier_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        // No competences.
    }

    // </editor-fold>
}
