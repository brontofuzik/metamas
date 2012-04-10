package example1.players;

import thespian4jade.core.Event;
import thespian4jade.behaviours.states.special.EventHandler;
import thespian4jade.example.RoleEnacterPlayer;

/**
 * 'Factorial computer' player - the player playing the 'Executor' role.
 * @author Lukáš Kúdela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class FactorialComputer_Player extends RoleEnacterPlayer {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /** The full name of the 'Executor' role. */
    private static final String EXECUTOR_ROLE_FULL_NAME
        = "functionInvocation_Organization.Executor_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the FactorialComputer_Player class.
     */
    public FactorialComputer_Player() {
        super(new RoleFullName(EXECUTOR_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();

        // Add responsibilities.
        addResponsibility(ExecuteFunction_Responsibility.class);
        
        // Schedule behaviours.
        // Role enactment
        scheduleEnactRole(4000);
        scheduleSubscribeToEvent(Event.ROLE_ACTIVATED, RoleActivated_EventHandler.class, 5000);
        scheduleSubscribeToEvent(Event.ROLE_DEACTIVATED, RoleDeactivated_EventHandler.class, 5000);
        
        // Role deactment
        scheduleDeactRole(12000);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Role activated' event handler.
     * @author Lukáš Kúdela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class RoleActivated_EventHandler
        extends EventHandler<FactorialComputer_Player> {

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
    public static class RoleDeactivated_EventHandler
        extends EventHandler<FactorialComputer_Player> {

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
    
    // </editor-fold>
}
