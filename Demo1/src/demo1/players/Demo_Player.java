package demo1.players;

import demo1.players.requirements.CalculateFactorial_Requirement;
import jade.core.behaviours.WakerBehaviour;
import jadeorg.core.player.Player;
import jadeorg.core.player.PlayerException;
import java.util.HashSet;
import java.util.Set;

/**
 * A Participant player.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Demo_Player extends Player {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    protected static final Set<String> abilities = new HashSet<String>();
    
    // ----- PRIVATE -----
    
    private RoleFullName roleFullName;
    
    private PowerFullName powerFullName;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    static {
        abilities.add("calculate-factorial"); // For the Answerer role.
    }
    
    // ----- PROTECTED -----
    
    protected Demo_Player(PowerFullName powerFullName) {
        roleFullName = powerFullName.getRoleFullName();
        this.powerFullName = powerFullName;
    }
    
    protected Demo_Player(RoleFullName roleFullName) {
        this.roleFullName = roleFullName;
        powerFullName = null;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
//    int getTimeout() {
//        return new Integer((String)getArguments()[0]).intValue();
//    }
    
//    List<RoleFullName> getRoles() {
//        List<RoleFullName> roles = new LinkedList<RoleFullName>();
//        for (int i = 1; i < getArguments().length; i++) {
//            roles.add(new RoleFullName((String)getArguments()[i]));
//        }
//        return roles;
//    }
            
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if all requirements can be met; <c>false</c> otherwise
     */
    @Override
    public boolean evaluateRequirements(String[] requirements) {
        return evaluateAllRequirements(requirements);
    }
    
    // ----- PROTECTED -----
    
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
        addBehaviour(new WakerBehaviour(this, timeout)
        {
            private Player getMyPlayer() {
                return (Player)myAgent;
            }
            
            @Override
            protected void handleElapsedTimeout() {
                try {
                    getMyPlayer().initiateEnactRole(roleFullName.getOrganizationName(), roleFullName.getRoleName());
                } catch (PlayerException ex) {
                    getMyPlayer().logSevere(String.format("Error: %1$s", ex.getMessage()));
                }
            }
        });
        
        // Initiate the 'Activate role' protocol.
        addBehaviour(new WakerBehaviour(this, timeout + 1000)
        {
            private Player getMyPlayer() {
                return (Player)myAgent;
            }
            
            @Override
            protected void handleElapsedTimeout() {
                try {
                    getMyPlayer().initiateActivateRole(roleFullName.getRoleName());
                } catch (PlayerException ex) {
                    getMyPlayer().logSevere(String.format("Error: %1$s", ex.getMessage()));
                }
            }
        });
    }
    
    private void invokePower(PowerFullName powerFullName, int timeout) {
        // Initiate the 'Invoke power' protocol.
        addBehaviour(new WakerBehaviour(this, timeout)
        {
            private Player getMyPlayer() {
                return (Player)myAgent;
            }
            
            @Override
            protected void handleElapsedTimeout() {
                try {
                    getMyPlayer().initiateInvokePower("demo1.organizations.powers.CalculateFactorial_Power", new Integer(10));
                } catch (PlayerException ex) {
                    getMyPlayer().logSevere(String.format("Error: %1$s", ex.getMessage()));
                }
            }
        });
    }
    
    private void deactivateAndDeactRole(final RoleFullName roleFullName, int timeout) {
        // Initiate the 'Deactivate role' protocol.
        addBehaviour(new WakerBehaviour(this, timeout)
        {
            private Player getMyPlayer() {
                return (Player)myAgent;
            }
            
            @Override
            protected void handleElapsedTimeout() {
                try {
                    getMyPlayer().initiateDeactivateRole(roleFullName.getRoleName());
                } catch (PlayerException ex) {
                    getMyPlayer().logSevere(String.format("Error: %1$s", ex.getMessage()));
                }
            }
        });
        
        // Initiate the 'Deact role' protocol.
        addBehaviour(new WakerBehaviour(this, timeout + 1000)
        {
            private Player getMyPlayer() {
                return (Player)myAgent;
            }
            
            @Override
            protected void handleElapsedTimeout() {
                try {
                    getMyPlayer().initiateDeactRole(roleFullName.getOrganizationName(), roleFullName.getRoleName());
                } catch (PlayerException ex) {
                    getMyPlayer().logSevere(String.format("Error: %1$s", ex.getMessage()));
                }
            }
        });
    }
    
    // TODO Consider moving this method to the Player superclass.
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if all requirement can be met; <c>false</c> otherwise
     */
    private boolean evaluateAllRequirements(String[] requirements) {
        for (String requirement : requirements) {
            if (evaluateRequirement(requirement)) {
                return false;
            }
        }
        return true;
    }
    
    // TODO Consider moving this method to the Player superclass.
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if any requirement can be met; <c>false</c> otherwise
     */
    private boolean evaluteAnyRequirement(String[] requirements) {
        for (String requirement : requirements) {
            if (evaluateRequirement(requirement)) {
                return true;
            }
        }
        return false;
    }
    
    // TODO Consider moving this method to the Player superclass. 
    /**
     * Evaluates a requirement.
     * @param requirement the requirement to evaluate
     * @return <c>true</c> if all requirements can be met; <c>false</c> otherwise 
     */
    private boolean evaluateRequirement(String requirement) {
        return abilities.contains(requirement);
    }
    
    // </editor-fold>
}
