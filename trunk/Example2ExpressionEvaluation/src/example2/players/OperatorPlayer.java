package example2.players;

import thespian4jade.behaviours.states.special.EventHandler;
import thespian4jade.example.RoleEnacterPlayer;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-20
 * @version %I% %G%
 */
public abstract class OperatorPlayer extends RoleEnacterPlayer {
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    protected OperatorPlayer(RoleFullName roleFullName) {
        super(roleFullName);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The 'Role activated' event handler.
     * @author Lukáš Kúdela
     * @since 2012-03-20
     * @version %I% %G%
     */
    public static class RoleActivated_EventHandler
        extends EventHandler<OperatorPlayer> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the 'Role activated' event.
         * @param roleName the name of the activated role
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Evaluator_Role")) {
                getMyPlayer().activateRole();
            }
        }
        
        // </editor-fold>
    }
    
    /**
     * The 'Role deactivated' event handler.
     * @author Lukáš Kúdela
     * @since 2012-03-20
     * @version %I% %G%
     */
    public static class RoleDeactivated_EventHandler
        extends EventHandler<OperatorPlayer> {

        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        /**
         * Handles the 'Role deactivated' event.
         * @param roleName the name of the deactivated role
         */
        @Override
        protected void handleEvent(String roleName) {
            if (roleName.equals("Evaluator_Role")) {
                getMyPlayer().deactivateRole();
            }
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
