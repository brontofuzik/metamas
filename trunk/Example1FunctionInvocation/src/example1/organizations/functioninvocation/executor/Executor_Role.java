package example1.organizations.functioninvocation.executor;

import thespian4jade.core.organization.Role;

/**
 * The 'Executor' role.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Executor_Role extends Role {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /** The name of the 'Executor' role. */
    public static final String NAME = "Executor_Role";
    
    /** The name of the 'Execute function' responsibility. */
    public static final String EXECUTE_FUNCTION_RESPONSIBILITY = "ExecuteFunction_Responsibility";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();

        // Add behaviours.
        addBehaviour(new Executor_Responder());
        logInfo("Behaviours added.");

        // Add competences.
        // No competences.
    }

    // </editor-fold>
}
