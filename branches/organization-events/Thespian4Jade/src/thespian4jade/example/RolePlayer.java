package thespian4jade.example;

import thespian4jade.core.player.Player;

/**
 * @author Lukáš Kúdela
 * @since
 * @version %I% %G%
 */
public class RolePlayer extends Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the role to enact and activate.
     */
    private RoleFullName roleFullName;   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new instance of the Demo_Player class.
     * @param roleFullName the full name of the role to enact and activate
     */
    public RolePlayer(RoleFullName roleFullName) {
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
        System.out.println("----- Enacting role: " + roleFullName.toString() + "-----");
        enactRole(roleFullName.getOrganizationName(), roleFullName.getRoleName());
    }
    
    /**
     * Schedules the role enactment.
     * @param timeout 
     */
    public void scheduleEnactRole(int timeout) {
        scheduleEnactRole(roleFullName, timeout);
    }
    
    /**
     * Deacts the role.
     */
    public void deactRole() {
        System.out.println("----- Deacting role: " + roleFullName.toString() + "-----");
        deactRole(roleFullName.getOrganizationName(), roleFullName.getRoleName());
    }
    
    /**
     * Schedules the role deactment.
     * @param timeout 
     */
    public void scheduleDeactRole(int timeout) {
        scheduleDeactRole(roleFullName, timeout);
    }
    
    /**
     * Activates the role.
     */
    public void activateRole() {
        System.out.println("----- Activating role: " + roleFullName.toString() + "-----");
        activateRole(roleFullName.getRoleName());
    }
    
    /**
     * Schedules the role activation.
     * @param timeout 
     */
    public void scheduleActivateRole(int timeout) {
        scheduleActivateRole(roleFullName, timeout);
    } 
    
    /**
     * Deactivates the role.
     */
    public void deactivateRole() {
        System.out.println("----- Deactivating role: " + roleFullName.toString() + "-----");
        deactivateRole(roleFullName.getRoleName());
    }
    
    /**
     * Shchedules the role deactivation.
     * @param timeout 
     */
    public void scheduleRoleDeactivation(int timeout) {
        scheduleDeactivateRole(roleFullName, timeout);
    }
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();       
    }

    // </editor-fold>
}
