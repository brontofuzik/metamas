package example1.organizations.functioninvocation;

import example1.organizations.functioninvocation.executor.Executor_Role;
import example1.organizations.functioninvocation.invoker.Invoker_Role;
import thespian4jade.core.organization.Organization;

/**
 * The 'Function invocation' oragnization.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class FunctionInvocation_Organization extends Organization {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {        
        super.setup();
        
        // Add roles.
        addRole(Invoker_Role.class);
        addRole(Executor_Role.class);
        logInfo("Roles added.");
    }
    
    // </editor-fold>
}
