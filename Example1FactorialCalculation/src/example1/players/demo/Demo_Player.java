package example1.players.demo;

import jadeorg.core.player.Player;

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
     * Initializes the Demo_Player class.
     */
    static {
        addAbility("calculate-factorial"); // For the Answerer role.
    }
    
    /**
     * Creates a new Demo player who will enact the Answerer role.
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
        
        // Add the requirements.
        addRequirement(CalculateFactorial_Requirement.class);
        
        int timeout = 2000;
        timeout = scheduleEnactRole(roleFullName, timeout);
        timeout = scheduleActivateRole(roleFullName, timeout);
        
        // Schedule individual behaviours.
        timeout = doScheduleBehaviours(timeout);

        timeout = scheduleDeactivateRole(roleFullName, timeout);
        scheduleDeactRole(roleFullName, timeout);
    }
    
    /**
     * Schedule individual behaviours.
     * Design pattern: Template method, Role: Primitive operation
     */
    protected abstract int doScheduleBehaviours(int timeout);

    // </editor-fold>
}
