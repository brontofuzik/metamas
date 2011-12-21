package jadeorg.core.organization;

import jadeorg.core.organization.kb.OrganizationKnowledgeBase;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

/**
 * An organization agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public abstract class Organization extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The organization roles. */
    Map<String, Class> roles = new Hashtable<String, Class>();
    
    /** The requirements. */
    Map<String, String[]> requirements = new Hashtable<String, String[]>();
    
    /** The knowledge base. */
    OrganizationKnowledgeBase knowledgeBase = new OrganizationKnowledgeBase();
    
    // ----- PRIVATE -----
    
    /** The DF agent description. */
    private DFAgentDescription agentDescription;

    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public Organization() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Enacts a role.
     * @param player the player
     */
    public void enactRoleResponder(AID playerAID) {
        logInfo("Responding to the 'Enact role' protocol.");
        
        addBehaviour(new Organization_EnactRoleResponder_New(playerAID));
    }

    /**
     * Deacts a role.
     * @param player the player
     */
    // TODO Move the precondition assertions to the 'Deact' protocol responder beahviour.
    public void deactRoleResponder(AID playerAID) {
        logInfo("Responding to the 'Deact role' protocol.");
        
        addBehaviour(new Organization_DeactRoleResponder_New(playerAID));
    }
    
    // ----- Logging -----
    
    /**
     * Logs a message.
     * @param level the level
     * @param message the message
     */
    public void log(Level level, String message) {
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("%1$s: %2$s", getLocalName(), message));
        }
    }
    
    /**
     * Logs an INFO-level message.
     * @param message the INFO-level message
     */
    public void logInfo(String message) {
        log(Level.INFO, message);
    }
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        addBehaviours();
        
        // TAG YellowPages
        //registerWithYellowPages();
    }

    /**
     * Adds a role.
     * @param roleClass the role class
     * @param requirements the role requirements
     */
    protected void addRole(Class roleClass, String[] requirements) {
        // ----- Preconditions -----
        if (roleClass == null) {
            throw new IllegalArgumentException("roleClass");
        }
        if (requirements == null) {
            throw new IllegalArgumentException("requirements");
        }
        // -------------------------
        
        String roleName = roleClass.getSimpleName();
        roles.put(roleName, roleClass);
        this.requirements.put(roleName, requirements);
        
        // TAG YellowPages
        //registerRoleWithDF(roleName);
        
        logInfo(String.format("Role (%1$s) added.", roleName));
    }
    
    /**
     * Adds a role.
     * @param roleClass the role class 
     */
    protected void addRole(Class roleClass) {        
        addRole(roleClass, new String[0]);
    }

    // ---------- PRIVATE ----------
    
    private void addBehaviours() {
        addBehaviour(new Organization_Manager());
        logInfo("Behaviours addded.");
    }

    // TAG YellowPages
    /**
     * Registers this organization with the Yellow pages.
     */
    private void registerWithYellowPages() {
        agentDescription = createOrganizationDescription();

        try {
            DFService.register(this, agentDescription);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        logInfo("Registered with the Yellow Pages.");
    }

    // TAG YellowPages
    private OrganizationAgentDescription createOrganizationDescription() {
        return new OrganizationAgentDescription(getAID());
    }

    // TAG YellowPages
    /**
     * Register a role with the Yellow pages.
     */
    private void registerRoleWithYellowPages(String roleName) {
        agentDescription.addServices(createRoleDescription(roleName));

        try {
            DFService.modify(this, agentDescription);
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        logInfo(String.format("Role (%1$) registered with the Yellow Pages.", roleName));
    }

    // TAG YellowPages
    private RoleServiceDescription createRoleDescription(String roleName) {
        return new RoleServiceDescription(roleName);
    } 

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    // TAG YellowPages
    /**
     * An organization agent description.
     */
    private static class OrganizationAgentDescription extends DFAgentDescription {

        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public OrganizationAgentDescription(AID organization) {
            super();
            setName(organization);
        }
        
        // </editor-fold>
    }

    // TAG YellowPages
    /**
     * A role service description.
     */
    private static class RoleServiceDescription extends ServiceDescription {

        // <editor-fold defaultstate="collapsed" desc="Constant fields">
        
        private static final String ROLE_SERVICE_TYPE = "role";

        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        public RoleServiceDescription(String roleName) {
            super();
            setType(ROLE_SERVICE_TYPE);
            setName(roleName);
        }
        
        // </editor-fold>
    }
}