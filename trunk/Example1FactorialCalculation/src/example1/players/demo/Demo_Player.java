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
        
        scheduleEnactRole(roleFullName, 5000);
        scheduleActivateRole(roleFullName, 6000);
        
        // Schedule individual behaviours.
        doScheduleBehaviours();

        scheduleDeactivateRole(roleFullName, 15000);
        scheduleDeactRole(roleFullName, 16000);
    }
    
    /**
     * Schedule individual behaviours.
     * Design pattern: Template method, Role: Primitive operation
     */
    protected abstract void doScheduleBehaviours();
  
    // ----- PRIVATE -----
    
    private void scheduleEnactRole(final RoleFullName roleFullName, int timeout) {
        // Initiate the 'Enact role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {    
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().enactRole(roleFullName.getOrganizationName(),
                    roleFullName.getRoleName());
            }
        });
    }

    private void scheduleActivateRole(final RoleFullName roleFullName, int timeout) {
        // Initiate the 'Activate role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {            
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().activateRole(roleFullName.getRoleName());
            }
        });
    }

    private void scheduleDeactivateRole(final RoleFullName roleFullName, int timeout) {
        // Initiate the 'Deactivate role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().deactivateRole(roleFullName.getRoleName());
            }
        });
    }

    private void scheduleDeactRole(final RoleFullName roleFullName, int timeout) {
        // Initiate the 'Deact role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().deactRole(roleFullName.getOrganizationName(),
                    roleFullName.getRoleName());
            }
        });
    }
   
    // </editor-fold>
}
