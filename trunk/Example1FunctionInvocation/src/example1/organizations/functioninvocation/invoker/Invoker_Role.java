package example1.organizations.functioninvocation.invoker;

import thespian4jade.core.organization.Role;

/**
 * The 'Invoker' role.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Invoker_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        // No behaviours.

        // Add competences.
        addCompetence(InvokeFunction_Competence.class);
        logInfo("Competences added.");
    }

    // </editor-fold>
}
