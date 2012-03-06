package jadeorg.core.player;

import jadeorg.core.player.kb.PlayerKnowledgeBase;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.util.Logger;
import jadeorg.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import jadeorg.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import jadeorg.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import jadeorg.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import jadeorg.proto.roleprotocol.invokepowerprotocol.InvokePowerProtocol;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * A player agent.
 * @author Lukáš Kúdela
 * @since 2011-10-17
 * @version %I% %G%
 */
public abstract class Player extends Agent {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    Map<String, Class> responsibilities = new HashMap<String, Class>();
    
    /** The knowledge base. */
    PlayerKnowledgeBase knowledgeBase = new PlayerKnowledgeBase();
    
    // ----- PRIVATE -----

    // TAG OBSOLETE
//    private Player_Initiator initiator = new Player_Initiator(this);
    
    /** The logger. */
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the Player class.
     */
    public Player() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Evaluates a set of responsibilities.
     * @param responsibilities the set of responsibilities to evaluate
     * @return <c>true</c> if all responsibilities can be met; <c>false</c> otherwise
     */
    public boolean evaluateResponsibilities(String[] responsibilities) {
        return evaluateAllResponsibilities(responsibilities);
    }
    
    // TAG OBSOLETE
//    public final void enactRole(String organizationName, String roleName) {
//        initiator.initiateProtocol(EnactRoleProtocol.getInstance(),
//            new Object[] { organizationName, roleName });
//    }
    
    /**
     * Enacts a role.
     * @param organizationName the name of the organization
     * @param roleName the name of the role to enact
     */
    protected final void enactRole(final String organizationName,
        final String roleName) {
        addBehaviour(EnactRoleProtocol.getInstance()
            .createInitiatorParty(new Object[] { organizationName, roleName }));
    }
    
    /**
     * Schedules a role enactment.
     * Takes 2 seconds.
     * @param roleFullName the full name of the role to enact
     * @param timeout the start timeout
     * @return the end timeout
     */
    protected final int scheduleEnactRole(final RoleFullName roleFullName,
        final int timeout) {
        // Initiate the 'Enact role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {    
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().enactRole(roleFullName.getOrganizationName(),
                    roleFullName.getRoleName());
            }
        });
        return timeout + 2000;
    }
    
    // TAG OBSOLETE
//    public final void deactRole(String organizationName, String roleName) {
//        initiator.initiateProtocol(DeactRoleProtocol.getInstance(),
//            new Object[] { organizationName, roleName });
//    }
    
    /**
     * Deacts a role.
     * @param organizationName the name of the organization
     * @param roleName the name of the role to deact
     */
    protected final void deactRole(final String organizationName,
        final String roleName) {
        addBehaviour(DeactRoleProtocol.getInstance()
            .createInitiatorParty(new Object[] { organizationName, roleName }));
    }
    
    /**
     * Schedules a role deactment.
     * Takes 2 seconds.
     * @param roleFullName the full name of the role to deact
     * @param timeout the start timeout
     * @return the end timeout
     */
    protected final int scheduleDeactRole(final RoleFullName roleFullName,
        final int timeout) {
        // Initiate the 'Deact role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().deactRole(roleFullName.getOrganizationName(),
                    roleFullName.getRoleName());
            }
        });
        return timeout + 2000;
    }
    
    // TAG OBSOLETE
//    public final activateRole(String roleName) {
//        initiator.initiateProtocol(ActivateRoleProtocol.getInstance(),
//            new Object[] { roleName });
//    }
    
    /**
     * Activates a role.
     * @param roleName the name of the role to activate
     */
    protected final void activateRole(final String roleName) {
        addBehaviour(ActivateRoleProtocol.getInstance()
            .createInitiatorParty(new Object[] { roleName }));
    }
    
    /**
     * Schedules a role activation.
     * Takes 2 seconds.
     * @param roleFullName the full name of the role to actiavte
     * @param timeout the start timeout
     * @return the end timeout
     */
    protected final int scheduleActivateRole(final RoleFullName roleFullName,
        final int timeout) {
        // Initiate the 'Activate role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {            
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().activateRole(roleFullName.getRoleName());
            }
        });
        return timeout + 2000;
    }
    
    // TAG OBSOLETE
//    public final void deactivateRole(String roleName) {
//        initiator.initiateProtocol(DeactivateRoleProtocol.getInstance(),
//            new Object[] { roleName });
//    }
    
    /**
     * Deactivates a role.
     * @param roleName the name of the role to deactivate
     */
    protected final void deactivateRole(final String roleName) {
        addBehaviour(DeactivateRoleProtocol.getInstance()
            .createInitiatorParty(new Object[] { roleName }));
    }
    
    /**
     * Schedules a role deactivation.
     * @param roleFullName the full name of the role to deactivate
     * @param timeout the start timeout
     * @return the end timeout
     */
    protected final int scheduleDeactivateRole(final RoleFullName roleFullName,
        final int timeout) {
        // Initiate the 'Deactivate role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().deactivateRole(roleFullName.getRoleName());
            }
        });
        return timeout + 2000;
    }
    
    // TAG OBSOLETE
//    public final void invokePower(String powerName, Object argument) {
//        initiator.initiateProtocol(InvokePowerProtocol.getInstance(),
//            new Object[] { powerName, argument });
//    }
    
    /**
     * Invokes a power.
     * @param powerName the name of the power to invoke
     * @param argument the power argument
     */
    protected final <T> void invokePower(final String powerName, final T argument) {
        addBehaviour(InvokePowerProtocol.getInstance()
            .createInitiatorParty(new Object[] { powerName, argument }));
    }
    
    /**
     * Schedules a power invocation.
     * @param powerFullName the full name of the power invoke
     * @param argument the power argument
     * @param timeout the start timeout
     * @return the end timeout
     */
    protected final <T> int scheduleInvokePower(final PowerFullName powerFullName,
        final T argument, final int timeout, final int duration) {
        // Initiate the 'Invoke power' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout)
        {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().invokePower(powerFullName.getPowerName(), argument);
            }
        });
        return timeout + duration;
    }  
    
    // ----- Logging -----
    
    /**
     * Logs a message.
     * @param level the log level
     * @param message the message to log
     */
    public void log(Level level, String message) {
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("%1$s: %2$s", getLocalName(), message));
        }
    }
    
    /**
     * Logs an INFO-level message.
     * @param message the message to log
     */
    public void logInfo(String message) {
        log(Level.INFO, message);
    }
    
    public void logSevere(String message) {
        log(Level.SEVERE, message);
    }
    
    // ---------- PROTECTED ----------
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add behaviours.
        addBehaviour(new Player_Responder());
        logInfo("Behaviours added.");
    }
    
    /**
     * Adds a requirement.
     * @param requirementClass the requirement class
     */
    protected final void addRequirement(Class requirementClass) {
        // ----- Preconditions -----
        if (requirementClass == null) {
            throw new IllegalArgumentException("requirementClass");
        }
        // -------------------------
        
        String requirementName = requirementClass.getSimpleName();
        responsibilities.put(requirementName, requirementClass);
        
        logInfo(String.format("Requirement (%1$s) added.", requirementName));
    }
    
    /**
     * Evaluates a set of responsibilities.
     * @param responsibilities the set of responsibilities to evaluate
     * @return <c>true</c> if all requirement can be met; <c>false</c> otherwise
     */
    protected final boolean evaluateAllResponsibilities(String[] responsibilities) {
        for (String requirement : responsibilities) {
            if (evaluateReponsibility(requirement)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Evaluates a set of responsibilities.
     * @param responsibilities the set of responsibilities to evaluate
     * @return <c>true</c> if any requirement can be met; <c>false</c> otherwise
     */
    protected final boolean evaluteAnyRequirement(String[] responsibilities) {
        for (String requirement : responsibilities) {
            if (evaluateReponsibility(requirement)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Evaluates a requirement.
     * @param requirement the requirement to evaluate
     * @return <c>true</c> if all responsibilities can be met; <c>false</c> otherwise 
     */
    protected /* virtual */ boolean evaluateReponsibility(String responsibility) {
        return responsibilities.containsKey(responsibility);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    protected abstract class PlayerWakerBehaviour extends WakerBehaviour {
    
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        protected PlayerWakerBehaviour(Player player, int timeout) {
            super(player, timeout);
        }
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and setters">
        
        protected Player getMyPlayer() {
            return (Player)myAgent;
        }
        
        // </editor-fold>
    }
    
    protected static class RoleFullName {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /** The name of organization instance. */
        private String organizationName;
        
        /** The name of roleToDeact class. */
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public RoleFullName(String organizationName, String roleName) {
            this.organizationName = organizationName;
            this.roleName = roleName;
        }
            
        public RoleFullName(String roleFullName) {
            String[] nameParts = roleFullName.split("\\.");
            organizationName = nameParts[0];
            roleName = nameParts[1];
        }
        
        // </editor-fold>  
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        public String getOrganizationName() {
            return organizationName;
        }
        
        public String getRoleName() {
            return roleName;
        }
        
        // </editor-fold>
    }
    
    protected static class PowerFullName {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        private RoleFullName roleFullName;
        
        private String powerName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public PowerFullName(String organizationName, String roleName, String powerName) {
            roleFullName = new RoleFullName(organizationName, roleName);
        }
        
        public PowerFullName(String powerFullName) {
            String[] nameParts = powerFullName.split("\\.");
            roleFullName = new RoleFullName(nameParts[0], nameParts[1]);
            powerName = nameParts[2];
        }
                
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        public RoleFullName getRoleFullName() {
            return roleFullName;
        }
        
        public String getOrganizationName() {
            return roleFullName.getOrganizationName();
        }
        
        public String getRoleName() {
            return roleFullName.getRoleName();
        }
        
        public String getPowerName() {
            return powerName;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
