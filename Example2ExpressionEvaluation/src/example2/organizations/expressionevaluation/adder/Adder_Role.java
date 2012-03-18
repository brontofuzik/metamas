package example2.organizations.expressionevaluation.adder;

import thespian4jade.core.organization.Role;

/**
 * The 'Adder' role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Adder_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    public static final String NAME = "Adder_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the Adder_Role class.
     */
    static {
        addResponsibility("Add_Responsibility");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Adder_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        // No competences.
    }

    // </editor-fold>
}
