package jadeorg.core.player;

import jadeorg.core.player.requirement.Requirement;
import jadeorg.core.player.kb.PlayerKnowledgeBase;
import jade.core.AID;
import jade.core.Agent;
import jade.util.Logger;
import java.util.logging.Level;

/**
 * A player agent.
 * @author Lukáš Kúdela
 * @since 2011-10-17
 * @version %I% %G%
 */
public abstract class Player extends Agent {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The knowledge base. */
    public PlayerKnowledgeBase knowledgeBase = new PlayerKnowledgeBase();
    
    /** The 'Meet requirement' responder party behaviour. */
    private Player_MeetRequirementResponder meetRequirementResponder = new Player_MeetRequirementResponder();
    
    /** The logger. */
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Player() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void initiateEnactRole(String organizationName, String roleName) throws PlayerException {
        logInfo(String.format("Initiating the 'Enact role' (%1$s.%2$s) protocol.", organizationName, roleName));
        
        // TAG YELLOW-PAGES
        //DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);
        
        // Check if the organization exists.
        AID organizationAID = new AID(organizationName, AID.ISLOCALNAME);
        if (organizationAID != null) {
            // The organization exists.
            addBehaviour(new Player_EnactRoleInitiator(organizationAID, roleName));
        } else {
            // The organization does not exist.
            String message = String.format("Error enacting a role. The organization '%1$s' does not exist.", organizationName);
            throw new PlayerException(this, message);
        }
    }
    
    // TODO Check if the role is enacted.
    public void initiateDeactRole(String organizationName, String roleName) throws PlayerException {        
        logInfo(String.format("Initiating the 'Deact role' (%1$s.%2$s) protocol.", organizationName, roleName));
        
        // TAG YellowPages
        //DFAgentDescription organization = YellowPages.searchOrganizationWithRole(this, organizationName, roleName);
        
        AID organizationAID = new AID(organizationName, AID.ISLOCALNAME);
        if (organizationAID != null) {
            // The organizaiton exists.
            addBehaviour(new Player_DeactRoleInitiator(organizationAID, roleName));
        } else {
            // The organization does not exist.
            String message = String.format("Error deacting a role. The organization '%1$s' does not exist.", organizationName);
            throw new PlayerException(this, message);
        }
    }
    
    public void initiateActivateRole(String roleName) throws PlayerException {
        logInfo(String.format("Initiating the 'Activate role' (%1$s) protocol.", roleName));
        
        // Check if the role can be activated.
        if (knowledgeBase.canActivateRole(roleName)) {
            // The role can be activated.
            AID roleAID = knowledgeBase.getEnactedRole(roleName).getRoleAID();
            addBehaviour(new Player_ActivateRoleInitiator(roleName, roleAID));
        } else {
            // The role can not be activated.
            String message = String.format("Error activating the role '%1$s'. It is not enacted.", roleName);
            throw new PlayerException(this, message);
        }
    }
    
    public void initiateDeactivateRole(String roleName) throws PlayerException {
        logInfo(String.format("Initiating the 'Deactivate role' (%1$s) protocol.", roleName));
        
        if (knowledgeBase.canDeactivateRole(roleName)) {
            // The role can be deactivated.
            addBehaviour(new Player_DeactivateRoleInitiator(roleName, knowledgeBase.getEnactedRole(roleName).getRoleAID()));
        } else {
            // The role can not be deactivated.
            String message = String.format("I cannot deactivate the role '%1$s' because I do not play it.", roleName);
            throw new PlayerException(this, message);
        }
    }
    
    public void initiateInvokePower(String powerName, Object argument) throws PlayerException {
        logInfo(String.format("Initiating the 'Invoke power' (%1$s) protocol.", powerName));
        
        if (knowledgeBase.canInvokePower(powerName)) {
            // The player can invoke the power.
            addBehaviour(new Player_InvokePowerInitiator(powerName, argument));
        } else {
            // The player can not invoke the power.
            String message = String.format("I cannot invoke the power '%1$s'.", powerName);
            throw new PlayerException(this, message);
        }
    }
    
    public void respondToMeetRequirement(String protocolId, AID roleAID) {
        logInfo("Responding to the 'Meet requirement' protocol.");
        
        if (roleAID.equals(knowledgeBase.getActiveRole().getRoleAID())) {
            // The sender role is the active role.
            meetRequirementResponder.setRoleAID(roleAID);
            addBehaviour(meetRequirementResponder);
        } else {
            // The sender role is not the active role.
            // TODO
        }
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
        addBehaviour(new Player_Manager());
        logInfo("Behaviours added.");
    }
    
    protected void addRequirement(Requirement requirement) {
        meetRequirementResponder.addRequirement(requirement);
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
