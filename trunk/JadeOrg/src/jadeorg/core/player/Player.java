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
    
//    public void initiateEnactRole_Old(String organizationName, String roleName) throws PlayerException {
//        logInfo(String.format("Initiating the 'Enact role' (%1$s.%2$s) protocol.", organizationName, roleName));
//        
//        // TAG YELLOW-PAGES
//        //DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);
//        
//        // Check if the organization exists.
//        AID organizationAID = new AID(organizationName, AID.ISLOCALNAME);
//        if (organizationAID != null) {
//            // The organization exists.
//            addBehaviour(new Player_EnactRoleInitiator(organizationAID, roleName));
//        } else {
//            // The organization does not exist.
//            String message = String.format("Error enacting a role. The organization '%1$s' does not exist.", organizationName);
//            throw new PlayerException(this, message);
//        }
//    }
    
    public void enactRole(String organizationName, String roleName) {
        initiator.initiateProtocol(EnactRoleProtocol.getInstance(),
            new Object[] { organizationName, roleName });
    }
    
//    // TODO Check if the role is enacted.
//    public void initiateDeactRole_Old(String organizationName, String roleName) throws PlayerException {        
//        logInfo(String.format("Initiating the 'Deact role' (%1$s.%2$s) protocol.", organizationName, roleName));
//        
//        // TAG YellowPages
//        //DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);
//        
//        AID organizationAID = new AID(organizationName, AID.ISLOCALNAME);
//        if (organizationAID != null) {
//            // The organizaiton exists.
//            addBehaviour(new Player_DeactRoleInitiator(organizationAID, roleName));
//        } else {
//            // The organization does not exist.
//            String message = String.format("Error deacting a role. The organization '%1$s' does not exist.", organizationName);
//            throw new PlayerException(this, message);
//        }
//    }
    
    // TODO Check if the role is enacted.
    public void deactRole(String organizationName, String roleName) {
        initiator.initiateProtocol(DeactRoleProtocol.getInstance(),
            new Object[] { organizationName, roleName });
    }
    
//    public void initiateActivateRole_Old(String roleName) throws PlayerException {
//        logInfo(String.format("Initiating the 'Activate role' (%1$s) protocol.", roleName));
//        
//        // Check if the role can be activated.
//        if (knowledgeBase.canActivateRole(roleName)) {
//            // The role can be activated.
//            AID roleAID = knowledgeBase.getEnactedRole(roleName).getRoleAID();
//            addBehaviour(new Player_ActivateRoleInitiator(roleName, roleAID));
//        } else {
//            // The role can not be activated.
//            String message = String.format("Error activating the role '%1$s'. It is not enacted.", roleName);
//            throw new PlayerException(this, message);
//        }
//    }
    
    public void activateRole(String roleName) {
        initiator.initiateProtocol(ActivateRoleProtocol.getInstance(),
            new Object[] { roleName });
    }
    
//    public void initiateDeactivateRole_Old(String roleName) throws PlayerException {
//        logInfo(String.format("Initiating the 'Deactivate role' (%1$s) protocol.", roleName));
//        
//        if (knowledgeBase.canDeactivateRole(roleName)) {
//            // The role can be deactivated.
//            addBehaviour(new Player_DeactivateRoleInitiator(roleName, knowledgeBase.getEnactedRole(roleName).getRoleAID()));
//        } else {
//            // The role can not be deactivated.
//            String message = String.format("I cannot deactivate the role '%1$s' because I do not play it.", roleName);
//            throw new PlayerException(this, message);
//        }
//    }
    
    public void deactivateRole(String roleName) {
        initiator.initiateProtocol(DeactivateRoleProtocol.getInstance(),
            new Object[] { roleName });
    }
    
//    public void initiateInvokePower_Old(String powerName, Object argument) throws PlayerException {
//        logInfo(String.format("Initiating the 'Invoke power' (%1$s) protocol.", powerName));
//        
//        if (knowledgeBase.canInvokePower(powerName)) {
//            // The player can invoke the power.
//            addBehaviour(new Player_InvokePowerInitiator(powerName, argument));
//        } else {
//            // The player can not invoke the power.
//            String message = String.format("I cannot invoke the power '%1$s'.", powerName);
//            throw new PlayerException(this, message);
//        }
//    }
    
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
