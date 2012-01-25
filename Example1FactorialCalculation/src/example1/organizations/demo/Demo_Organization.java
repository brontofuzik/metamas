package example1.organizations.demo;

import example1.organizations.demo.answerer.Answerer_Role;
import example1.organizations.demo.asker.Asker_Role;
import jadeorg.core.organization.Organization;

/**
 * An English auction organization.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Demo_Organization extends Organization {

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {        
        super.setup();
        
        // Add roles.
        addRole(Asker_Role.class);
        addRole(Answerer_Role.class);
        logInfo("Roles added.");
    }
    
    // </editor-fold>
}
