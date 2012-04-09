package thespian4jade.example;

import thespian4jade.core.Event;
import thespian4jade.core.player.Player;

/**
 * Role-enacter player - a player whose intention is to enact a role
 * in an organization, and both the role and the organization are predetermined.
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public class RoleEnacterPlayer extends Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the role to enact.
     */
    private RoleFullName roleFullName;   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the RoleEnacterPlayer class.
     * @param roleFullName the full name of the role to enact
     */
    public RoleEnacterPlayer(RoleFullName roleFullName) {
        // ----- Preconditions -----
        assert roleFullName != null;
        // -------------------------
        
        this.roleFullName = roleFullName;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Enacts the role.
     */
    public void enactRole() {
        System.out.println("----- Enacting role: " + roleFullName.toString() + " -----");
        enactRole(roleFullName.getOrganizationName(), roleFullName.getRoleName());
    }
    
    /**
     * Schedules the role enactment.
     * @param timeout the timeout of execution
     */
    public void scheduleEnactRole(int timeout) {
        System.out.println("----- Scheduling role enactment: " + roleFullName.toString() + " -----");
        scheduleEnactRole(roleFullName, timeout);
    }
    
    /**
     * Deacts the role.
     */
    public void deactRole() {
        System.out.println("----- Deacting role: " + roleFullName.toString() + " -----");
        deactRole(roleFullName.getOrganizationName(), roleFullName.getRoleName());
    }
    
    /**
     * Schedules the role deactment.
     * @param timeout the timeout of execution
     */
    public void scheduleDeactRole(int timeout) {
        System.out.println("----- Scheduling role deactment: " + roleFullName.toString() + " -----");
        scheduleDeactRole(roleFullName, timeout);
    }
    
    /**
     * Activates the role.
     */
    public void activateRole() {
        System.out.println("----- Activating role: " + roleFullName.toString() + " -----");
        activateRole(roleFullName.getRoleName());
    }
    
    /**
     * Schedules the role activation.
     * @param timeout the timeout of execution
     */
    public void scheduleActivateRole(int timeout) {
        System.out.println("----- Scheduling role activation: " + roleFullName.toString() + " -----");
        scheduleActivateRole(roleFullName, timeout);
    } 
    
    /**
     * Deactivates the role.
     */
    public void deactivateRole() {
        System.out.println("----- Deactivating role: " + roleFullName.toString() + " -----");
        deactivateRole(roleFullName.getRoleName());
    }
    
    /**
     * Shchedules the role deactivation.
     * @param timeout the timeout of execution
     */
    public void scheduleDeactivateRole(int timeout) {
        System.out.println("----- Scheduling role deactivation: " + roleFullName.toString() + " -----");
        scheduleDeactivateRole(roleFullName, timeout);
    }
    
    /**
     * Schedules the event subscription.
     * @param event the event
     * @param eventHandlerClass the event handlers
     * @param timeout the timeout of execution
     */
    public void scheduleSubscribeToEvent(Event event, Class eventHandlerClass, int timeout) {
        System.out.println("----- Scheduling event subscription: " + event.toString() + " -----");
        scheduleSubscribeToEvent(roleFullName.getOrganizationName(), event, eventHandlerClass, timeout);
    }
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();       
    }

    // </editor-fold>
}
