package example1.players;

import thespian4jade.core.Event;
import thespian4jade.core.player.EventHandler;
import thespian4jade.example.RolePlayer;

/**
 * Player2 - he player playing the 'Executer' role.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class Player2 extends RolePlayer {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String EXECUTER_ROLE_FULL_NAME
        = "functionInvocation_Organization.Executer_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new Demo player who will enact the Executer role.
     * @param roleFullName the full name of the role to enact and activate
     */
    public Player2() {
        super(new RoleFullName(EXECUTER_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();

        // Add responsibilities.
        addResponsibility(ExecuteFunction_Responsibility.class);
        
        // Add event handlers.
        addEventHandler(Event.ROLE_ACTIVATED, Demo2_InvokerRoleActivated_EventHandler.class);
        addEventHandler(Event.ROLE_DEACTIVATED, Demo2_InvokerRoleDeactivated_EventHandler.class);
        addEventHandler(Event.ROLE_DEACTED, Demo2_InvokerRoleDeacted_EventHandler.class);
        
        // Schedule behaviours.
        scheduleEnactRole(4000);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Role activated' event handler.
     * @author Lukáš Kúdela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class Demo2_InvokerRoleActivated_EventHandler
        extends EventHandler<Player2> {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Handles the 'Role activated' event.
         * @param roleName the name of the activated role
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Invoker_Role")) {
                getMyPlayer().activateRole();
            }
        }    

        // </editor-fold>   
    }
    
    /**
     * The 'Role deactivated' event handler.
     * @author Lukáš Kúdela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class Demo2_InvokerRoleDeactivated_EventHandler
        extends EventHandler<Player2> {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Handles the 'Role deactivated' event.
         * @param roleName the name of the deactivated role.
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Invoker_Role")) {
                getMyPlayer().deactivateRole();
            }
        }    

        // </editor-fold> 
    }
    
    /**
     * The 'Role deacted' event handler.
     * @author Lukáš Kúdela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class Demo2_InvokerRoleDeacted_EventHandler
        extends EventHandler<Player2> {

        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Handles the 'Role deacted' event.
         * @param roleName the name of the deacted role
         */
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
