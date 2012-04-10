package example2.organizations.expressionevaluation.evaluator;

import thespian4jade.core.organization.Role;

/**
 * The 'Evaluator' role.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Evaluator_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /** The name of the 'Evaluator' role. */
    public static final String NAME = "Evaluator_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Evaluator_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        addCompetence(Evaluate_Competence.class);
        logInfo("Competences added.");
    }

    // </editor-fold>
}
