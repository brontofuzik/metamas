package demo1.organizations;

import demo1.organizations.powers.ComputeFactorial_Power;
import jadeorg.core.organization.Organization;
import jadeorg.core.organization.Role;

/**
 * An English auction organization.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class DemoOrganization extends Organization {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {        
        super.setup();
        
        addRoles();
    }
    
    // ----- PRIVATE -----
    
    private void addRoles() {
        addRole(Asker.class);
        addRole(Answerer.class);
        logInfo("Roles added.");
    }
    
    // </editor-fold>
    
    /**
     * An Asker role.
     * @author Lukáš Kúdela
     * @since 2011-11-20
     * @version %I% %G%
     */
    public class Asker extends Role {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void setup() {
            super.setup();
            
            // Add powers.
            addPower(new ComputeFactorial_Power());
        }
        
        // </editor-fold>
    }
    
    /**
     * An Answerer role.
     * @author Lukáš Kúdela
     * @since 2011-11-20
     * @version %I% %G%
     */
    public class Answerer extends Role {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        @Override
        protected void setup() {
            super.setup();
            
            // Add powers.
            // No powers.
        }
        
        // </editor-fold>
    }
}
