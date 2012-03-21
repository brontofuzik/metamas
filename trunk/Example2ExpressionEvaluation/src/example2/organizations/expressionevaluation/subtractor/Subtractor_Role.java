package example2.organizations.expressionevaluation.subtractor;

import thespian4jade.core.organization.Role;

/**
 * The 'Subtractor' role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Subtractor_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    public static final String NAME = "Subtractor_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Subtractor_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        // No competences.
    }

    // </editor-fold>
}
