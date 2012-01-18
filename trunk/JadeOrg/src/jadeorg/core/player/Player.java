package jadeorg.core.player;

import jadeorg.core.player.kb.PlayerKnowledgeBase;
import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
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
    
    Map<String, Class> requirements = new HashMap<String, Class>();
    
    /** The knowledge base. */
    PlayerKnowledgeBase knowledgeBase = new PlayerKnowledgeBase();
    
    // ----- PRIVATE -----
    
    private Player_Initiator initiator = new Player_Initiator(this);
    
    /** The logger. */
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void enactRole(String organizationName, String roleName) {
        initiator.initiateProtocol(EnactRoleProtocol.getInstance(),
            new Object[] { organizationName, roleName });
    }
    
    // TODO Check if the role is enacted.
    public void deactRole(String organizationName, String roleName) {
        initiator.initiateProtocol(DeactRoleProtocol.getInstance(),
            new Object[] { organizationName, roleName });
    }
    
    public void activateRole(String roleName) {
        initiator.initiateProtocol(ActivateRoleProtocol.getInstance(),
            new Object[] { roleName });
    }
    
    public void deactivateRole(String roleName) {
        initiator.initiateProtocol(DeactivateRoleProtocol.getInstance(),
            new Object[] { roleName });
    }
    
    public void invokePower(String powerName, Object argument) {
        initiator.initiateProtocol(InvokePowerProtocol.getInstance(),
            new Object[] { powerName, argument });
    }
    
    public abstract boolean evaluateRequirements(String[] requirements);
    
    // ----- Logging -----
    
    /**
     * Logs a requirementsInformMessage.
     * @param level the level
     * @param requirementsInformMessage the requirementsInformMessage
     */
    public void log(Level level, String message) {
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("%1$s: %2$s", getLocalName(), message));
        }
    }
    
    /**
     * Logs an INFO-level requirementsInformMessage.
     * @param requirementsInformMessage the INFO-level requirementsInformMessage
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
    
    protected void addRequirement(Class requirementClass) {
        // ----- Preconditions -----
        if (requirementClass == null) {
            throw new IllegalArgumentException("requirementClass");
        }
        // -------------------------
        
        String requirementName = requirementClass.getName();
        requirements.put(requirementName, requirementClass);
        
        logInfo(String.format("Requirement (%1$s) added.", requirementName));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
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
