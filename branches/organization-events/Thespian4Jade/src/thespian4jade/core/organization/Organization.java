package thespian4jade.core.organization;

import thespian4jade.core.organization.kb.OrganizationKnowledgeBase;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import thespian4jade.proto.Party;
import thespian4jade.proto.organizationprotocol.raiseeventprotocol.RaiseEventProtocol;

/**
 * An organization agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public abstract class Organization extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The role definitions.
     */
    Map<String, RoleDefinition> roles = new Hashtable<String, RoleDefinition>();
    
    /**
     * The knowledge base.
     * The knowledge base stores knowledge acquired at run time.
     */
    OrganizationKnowledgeBase knowledgeBase = new OrganizationKnowledgeBase();
    
    // ----- PRIVATE -----
    
    /**
     * The DF agent description.
     */
    private DFAgentDescription agentDescription;

    /**
     * The logger.
     */
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the Organization class.
     */
    public Organization() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
 
    /**
     * Gets an instance of a specified role.
     * 
     * @param roleName the name of the role
     * @return an instance of the specified role
     */
    public AID getRoleInstance(String roleName) {
        return knowledgeBase.getFirstRoleInstance(roleName);
    }
    
    /**
     * Gets all instances of a specified role.
     * @param roleName the name of the role.
     * @return all instances of the specified role
     */
    public Set<AID> getAllRoleInstances(String roleName) {
        return knowledgeBase.getAllRoleInstances(roleName);
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
        super.setup();
        
        // Add behaviours.
        addBehaviour(new Organization_Responder());
        logInfo("Behaviours addded.");
        
        // TAG YellowPages
        //registerWithYellowPages();
    }

    /**
     * Adds a role.
     * @param roleClass the role class
     * @param multiplicity the role multiplicity (single or multiple)
     * @param responsibilities the role responsibilities
     */
    protected void addRole(Class roleClass, Multiplicity multiplicity, String[] responsibilities) {
        // ----- Preconditions -----
        if (roleClass == null) {
            throw new IllegalArgumentException("roleClass");
        }
        if (responsibilities == null) {
            throw new IllegalArgumentException("responsibilities");
        }
        // -------------------------
        
        RoleDefinition roleDefinition = new RoleDefinition(roleClass, multiplicity, responsibilities);
        roles.put(roleDefinition.getName(), roleDefinition);
        
        // TAG YellowPages
        //registerRoleWithDF(roleName);
        
        logInfo(String.format("Role (%1$s) added.", roleDefinition.getName()));
        
        // Alternative:
        //addRole(new RoleDefinition(roleClass, responsibilities, multiplicity));
    }
    
    /**
     * Adds a role with the default responsibilities (none).
     * @param roleClass the role class
     * @param multiplicity the role multiplicity
     */
    protected void addRole(Class roleClass, Multiplicity multiplicity) {
        addRole(roleClass, multiplicity, new String[] {});
        
        // Alternative:
        //addRole(new RoleDefinition(roleClass, multiplicity));
    }
    
    /**
     * Adds a role with the default multiplicity (single).
     * @param roleClass the role class 
     * @responsibilities the role responsibilities
     */
    protected void addRole(Class roleClass, String[] responsibilities) {        
        addRole(roleClass, Multiplicity.SINGLE, responsibilities);
        
        // TODO (priority: high) Check if the following is necessary.
        // Alternative:
        addRole(new RoleDefinition(roleClass, responsibilities));
    }
    
    /**
     * Adds a role with the default responsibilities (none) and default multiplicity (single).
     * @param roleClass the role class
     */
    protected void addRole(Class roleClass) {
        addRole(roleClass, Multiplicity.SINGLE, new String[] {});
        
        // Alternative:
        //addRole(new RoleDefinition(roleClass));
    }
    
    /**
     * Adds a role definition.
     * @param roleDefinition the role definition
     */
    protected void addRole(RoleDefinition roleDefinition) {
        // ----- Preconditions -----
        if (roleDefinition == null) {
            throw new NullPointerException("roleDefinition");
        }
        // -------------------------
        
        roles.put(roleDefinition.getName(), roleDefinition);
        
        // TAG YellowPages
        //registerRoleWithDF(roleName);
        
        logInfo(String.format("Role (%1$s) added.", roleDefinition.getName()));
    }

    /**
     * Raises an event.
     * @param event the event to raise
     */
    protected final void raiseEvent(final String event, final String argument) {
        // ----- Preconditions -----
        assert event != null && !event.isEmpty();
        // -------------------------
        
        // Create a 'Raise event' protocol initiator party.
        Party raiseEventInitiator = RaiseEventProtocol.getInstance()
            .createInitiatorParty(event, argument);
        
        // Schedule the initiator party for execution.
        addBehaviour(raiseEventInitiator);
    }
    
    // ---------- PRIVATE ----------
    
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
    
    // </editor-fold>
}