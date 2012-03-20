package example1.players;

import thespian4jade.concurrency.Future;
import thespian4jade.concurrency.IObservable;
import thespian4jade.concurrency.IObserver;
import thespian4jade.core.player.EventHandler;
import thespian4jade.example.CompetencePlayer;

/**
 * Player1 - the player playing the 'Invoker' role.
 * @author Luk� K�dela
 * @since 2011-12-31
 * @version %I% %G%
 */
public class Player1 extends CompetencePlayer implements IObserver {
    
    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    private static final String INVOKE_FUNCTION_COMPETENCE_FULL_NAME
        = "functionInvocation_Organization.Invoker_Role.InvokeFunction_Competence";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Demo1_Player class.
     * Creates the demo1 player who will enact the 'Invoker' role.
     * @param competenceFullName the full name of the competence to invoke 
     */
    public Player1() {
        super(new CompetenceFullName(INVOKE_FUNCTION_COMPETENCE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    @Override
    public void update(IObservable observable) {
        // Get the 'Invoke function' competence result.
        Integer invokeFunctionResult = ((Future<Integer>)observable).getValue();
        System.out.println("----- 'Invoke function' competence result: " + invokeFunctionResult + " -----");
        
        deactivateRole();
    }   

    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add event handlers.
        addEventHandler("role-enacted", RoleEnacted_EventHandler.class);
        addEventHandler("role-activated", RoleActivated_EventHandler.class);
        addEventHandler("role-deactivated", RoleDeactivated_EventHandler.class);
        
        // Schedule behaviours.
        scheduleEnactRole(2000);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Role enacted' event handler.
     * @author Luk� K�dela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class RoleEnacted_EventHandler
        extends EventHandler<Player1> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Hanles the 'Role enacted' event handler.
         * @param roleName the name of the enacted role
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Executer_Role")) {
                getMyPlayer().activateRole();
            }
        }

        // </editor-fold>
    }
    
    /**
     * The 'Role activated' event handler.
     * @author Luk� K�dela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class RoleActivated_EventHandler
        extends EventHandler<Player1> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">

        /**
         * Handles the 'Role activated' event.
         * @param roleName the name of the activated role
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Executer_Role")) {
                // Set the 'Invoke function' competence argument. 
                Integer invokeFunctionArgument = new Integer(10);
                System.out.println("----- Invoke function argument: " + invokeFunctionArgument + " -----");
                
                Future<Integer> future = getMyPlayer().invokeCompetence(invokeFunctionArgument);
                future.addObserver(getMyPlayer());
            }
        }
    
        // </editor-fold>    
    }
    
    /**
     * The 'Role deactivated' event handler.
     * @author Luk� K�dela
     * @since 2012-03-19
     * @version %I% %G%
     */
    public static class RoleDeactivated_EventHandler
        extends EventHandler<Player1> {
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
    
        /**
         * Handles the 'Role deactivated' event.
         * @param roleName the name of the deactivated role
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Executer_Role")) {
                getMyPlayer().deactRole();
            }
        }
    
        // </editor-fold>  
    }
      
    // </editor-fold>
}
