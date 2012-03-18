package example2.players.calculator;

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
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibilities.
        addResponsibility(Add_Responsibility.class);
        addResponsibility(Subtract_Responsibility.class);
        addResponsibility(Multiply_Responsibility.class);
        addResponsibility(Divide_Responsibility.class);
        
        // Fur seconds before any interaction begins.
        int timeout = 4000;
        
        // Enact and activate the role.
        timeout = scheduleEnactRole(roleFullName, timeout);
        timeout = scheduleActivateRole(roleFullName, timeout);
        
        // Schedule competence invocations.
        timeout = doScheduleCompetenceInvocations(timeout);

        // Deactivate and deact the role.
        timeout = scheduleDeactivateRole(roleFullName, timeout);
        scheduleDeactRole(roleFullName, timeout);
    }
    
    /**
     * Schedule competence invocations.
     * Design pattern: Template method, Role: Primitive operation
     */
    protected abstract int doScheduleCompetenceInvocations(int timeout);
    
    // </editor-fold>
}
