package example3.organizations.expressionevaluation.evaluator;

import thespian4jade.core.organization.Role;

/**
 * 
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class Evaluator_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Evaluator_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        addPower(Evaluate_Competence.class);
        logInfo("Competences added.");
    }

    // </editor-fold>
}
