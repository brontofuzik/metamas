package example2.players;

import thespian4jade.core.player.Player;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-12
 * @version %I% %G%
 */
public abstract class Calculator_Player extends Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the role to enact and activate.
     */
    private RoleFullName roleFullName;   
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Creates a new instance of the Calculator_Player class.
     * @param roleFullName the full name of the role to enact and activate
     */
    Calculator_Player(RoleFullName roleFullName) {
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
    
    void scheduleEnactRole(int timeout) {
        scheduleEnactRole(roleFullName, timeout);
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
        addResponsibility(Add_Responsibility.class);
        addResponsibility(Subtract_Responsibility.class);
        addResponsibility(Multiply_Responsibility.class);
        addResponsibility(Divide_Responsibility.class);
    }
    
    // </editor-fold>
}
