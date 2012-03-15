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
     
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibilities.
        addRequirement(ExecuteFunction_Responsibility.class);
        
        // Four seconds before any interaction begins.
        int timeout = 4000;
        
        // Enact and activate the role.
        timeout = scheduleEnactRole(roleFullName, timeout);
        timeout = scheduleActivateRole(roleFullName, timeout);
        
        // Schedule competence invocations.
        timeout = doScheduleCompetenceInvocations(timeout);

        // Deact and deactivate the role.
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
