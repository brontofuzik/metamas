package example2.players.calculator;

import thespian4jade.core.player.EventHandler;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-20
 * @version %I% %G%
 */
public abstract class CalculatorOperator_Player extends Calculator_Player {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    CalculatorOperator_Player(RoleFullName roleFullName) {
        super(roleFullName);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add event handlers.
        addEventHandler("role-activated", Demo2_InvokerRoleActivated_EventHandler.class);
        addEventHandler("role-deactivated", Demo2_InvokerRoleDeactivated_EventHandler.class);
        addEventHandler("role-deacted", Demo2_InvokerRoleDeacted_EventHandler.class);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * @author Lukáš Kúdela
     * @since 2012-03-20
     * @version %I% %G%
     */
    private class Demo2_InvokerRoleActivated_EventHandler
        extends EventHandler<CalculatorOperator_Player> {

        @Override
        protected void handleEvent(String argument) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * @author Lukáš Kúdela
     * @since 2012-03-20
     * @version %I% %G%
     */
    private class Demo2_InvokerRoleDeactivated_EventHandler
        extends EventHandler<CalculatorOperator_Player> {

        @Override
        protected void handleEvent(String argument) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    /**
     * @author Lukáš Kúdela
     * @since 2012-03-20
     * @version %I% %G%
     */
    private class Demo2_InvokerRoleDeacted_EventHandler
        extends EventHandler<CalculatorOperator_Player> {

        @Override
        protected void handleEvent(String argument) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
    // </editor-fold>
}
