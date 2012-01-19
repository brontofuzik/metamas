package example1.organizations.demo;

import example1.organizations.demo.answerer.Answerer_Responder;
import example1.organizations.demo.asker.CalculateFactorial_Power;
import jade.lang.acl.ACLMessage;
import jadeorg.core.organization.Organization;
import jadeorg.core.organization.Role;

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
        addRole(Asker.class);
        addRole(Answerer.class);
        logInfo("Roles added.");
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
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
            
            // Add behaviours.
            // No behaviours.
            
            // Add powers.
            addPower(CalculateFactorial_Power.class);
            logInfo("Powers added.");
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
            
            // Add behaviours.
            addBehaviour(new Answerer_Responder());
            logInfo("Behaviours added.");
            
            // Add powers.
            // No powers.
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
