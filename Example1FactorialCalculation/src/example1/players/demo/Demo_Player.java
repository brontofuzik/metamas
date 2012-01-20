package example1.players.demo;

import jadeorg.core.player.Player;

/**
 * A Demo player.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Demo_Player extends Player {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the role to enact and activate.
     */
    private RoleFullName roleFullName;
    
    /**
     * The full name of the power to invoke. 
     */
    private PowerFullName powerFullName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes the Demo_Player class.
     */
    static {
        addAbility("calculate-factorial"); // For the Answerer role.
    }
       
    /**
     * Creates a new Demo player who will enact the Asker role.
     * @param powerFullName the full name of the power to invoke 
     */
    Demo_Player(PowerFullName powerFullName) {
        roleFullName = powerFullName.getRoleFullName();
        this.powerFullName = powerFullName;
    }
    
    /**
     * Creates a new Demo player who will enact the Answerer role.
     * @param roleFullName the full name of the role to enact and activate
     */
    Demo_Player(RoleFullName roleFullName) {
        this.roleFullName = roleFullName;
        powerFullName = null;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
     
    @Override
    protected void setup() {
        super.setup();
        
        // Add the requirements.
        addRequirement(CalculateFactorial_Requirement.class);
        
        if (roleFullName != null) {
            enactAndActivateRole(roleFullName, 5000);
        }
        
        if (powerFullName != null) {
            invokePower(powerFullName, 10000);
        }
        
        if (roleFullName != null) {
            deactivateAndDeactRole(roleFullName, 15000);
        }
    }
  
    // ----- PRIVATE -----
    
    private void enactAndActivateRole(final RoleFullName roleFullName, int timeout) {
        // Initiate the 'Enact role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {    
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().enactRole(roleFullName.getOrganizationName(),
                    roleFullName.getRoleName());
            }
        });
        
        // Initiate the 'Activate role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout + 1000)
        {            
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().activateRole(roleFullName.getRoleName());
            }
        });
    }
    
    private void invokePower(final PowerFullName powerFullName, int timeout) {
        // Initiate the 'Invoke power' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().invokePower(powerFullName.getPowerName(),
                    new Integer(10));
            }
        });
    }
    
    private void deactivateAndDeactRole(final RoleFullName roleFullName, int timeout) {
        // Initiate the 'Deactivate role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().deactivateRole(roleFullName.getRoleName());
            }
        });
        
        // Initiate the 'Deact role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout + 1000)
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
