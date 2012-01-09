package demo1.organizations;

import demo1.organizations.powers.CalculateFactorial_Power;
import jade.core.AID;
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
        
        // Add roles.
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
        
        /**
         * Responds to the 'Calculate factorial' protocol.
         * @param protocolId the protocol id
         * @param askerAID the asker AID
         */
        void respondToCalculateFactorial(String protocolId, AID askerAID) {
            logInfo(String.format("Responding to the 'Calculate factorial' protocol (id = %1$s).",
                protocolId));
        
            addBehaviour(new Answerer_CalculateFactorialResponder(protocolId, askerAID));
        }
        
        // ----- PROTECTED -----
        
        @Override
        protected void setup() {
            super.setup();
            
            // Add behaviours.
            addBehaviour(new Answerer_Manager());
            logInfo("Behaviours added.");
        }
        
        // </editor-fold>
    }
}
