package example1.players.demo;

import thespian4jade.core.player.Player;

/**
 * A Demo player.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public abstract class Demo_Player extends Player {

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
    Demo_Player(RoleFullName roleFullName) {
        // ----- Preconditions -----
        assert roleFullName != null;
        // -------------------------
        
        this.roleFullName = roleFullName;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    void enactRole() {
        enactRole(roleFullName.getOrganizationName(), roleFullName.getRoleName());
    }
    
    void deactRole() {
        deactRole(roleFullName.getOrganizationName(), roleFullName.getRoleName());
    }
    
    void activateRole() {
        activateRole(roleFullName.getRoleName());
    }
    
    void deactivateRole() {
        deactivateRole(roleFullName.getRoleName());
    }
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibilities.
        addResponsibility(ExecuteFunction_Responsibility.class);        
    }
    
    protected void scheduleEnactRole(int timeout) {
        scheduleEnactRole(roleFullName, timeout);
    }
    
    protected void scheduleDeactivateRole(int timeout) {
        scheduleDeactivateRole(roleFullName, timeout);
    }

    // </editor-fold>
}
