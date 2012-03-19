package example1.players.demo;

import thespian4jade.core.player.EventHandler;

/**
 * The Demo2 player. The player playing the 'Executer' role.
 * @author Luk� K�dela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class Demo2_Player extends Demo_Player {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new Demo player who will enact the Executer role.
     * @param roleFullName the full name of the role to enact and activate
     */
    public Demo2_Player() {
        super(new RoleFullName("functionInvocation_Organization.Executer_Role"));
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
        
        // Schedule behaviours.
        scheduleEnactRole(4000);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * @author Luk� K�dela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class Demo2_InvokerRoleActivated_EventHandler
        extends EventHandler<Demo2_Player> {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Invoker_Role")) {
                getMyPlayer().activateRole();
            }
        }    

        // </editor-fold>   
    }
    
    /**
     * @author Luk� K�dela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class Demo2_InvokerRoleDeactivated_EventHandler
        extends EventHandler<Demo2_Player> {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Invoker_Role")) {
                getMyPlayer().deactivateRole();
            }
        }    

        // </editor-fold> 
    }
    
    /**
     * @author Luk� K�dela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class Demo2_InvokerRoleDeacted_EventHandler
        extends EventHandler<Demo2_Player> {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Invoker_Role")) {
                getMyPlayer().deactRole();
            }
        }    

        // </editor-fold>
    }
    
    // </editor-fold>
}
