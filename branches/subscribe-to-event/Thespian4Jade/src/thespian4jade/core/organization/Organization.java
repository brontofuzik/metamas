package thespian4jade.core.organization;

import thespian4jade.core.organization.kb.OrganizationKnowledgeBase;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import thespian4jade.core.Event;
import thespian4jade.behaviours.parties.Party;
import thespian4jade.protocols.ProtocolRegistry_StaticClass;
import thespian4jade.protocols.Protocols;

/**
 * An organization agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public abstract class Organization extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The organization type's roles.
     * Design-time state.
     */
    final Map<String, RoleDefinition> roles = new HashMap<String, RoleDefinition>();
    
    /**
     * The organization's position holder.
     * Run-time state.
     */
    final PositionHolder positionHolder = new PositionHolder();
    
    /**
     * The organization's knowledge base.
     * The knowledge base stores knowledge acquired at run time.
     * Run-time state.
     */
    final OrganizationKnowledgeBase knowledgeBase = new OrganizationKnowledgeBase();
    
    // ----- PRIVATE -----
    
    // TAG YELLOW-PAGES
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
 
    // ----- Position management -----
    
    void addPosition(Role role) {
        positionHolder.addPosition(role);
        role.setMyOrganization(this);
    }
    
    void removePosition(Role role) {
        positionHolder.removePosition(role);
        role.setMyOrganization(null);
    }
    
    /**
     * Gets an active position of a specified role.
     * @param roleName the name of the role
     * @return a position of the specified role
     */
    public Role getActivePosition(String roleName) {
        return positionHolder.getActivePosition(roleName);
    }
    
    /**
     * Gets all active positions of a specified role.
     * @param roleName the name of the role.
     * @return all positions of the specified role
     */
    public List<Role> getAllActivePositions(String roleName) {
        return positionHolder.getAllActivePositions(roleName);
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
        
        // TAG YELLOW-PAGES
        //registerWithYellowPages();
    }
    
    // ----- Role management -----

    /**
     * Adds a role.
     * Design-time behaviour.
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
        
        // TAG YELLOW-PAGES
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
        
        // Alternative:
        //addRole(new RoleDefinition(roleClass, responsibilities));
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
        
        // TAG YELLOW-PAGES
        //registerRoleWithDF(roleName);
        
        logInfo(String.format("Role (%1$s) added.", roleDefinition.getName()));
    }

    /**
     * Publishes an event.
     * @param event the event to publish
     * @param argument the event argument
     * @param playerToExclude the payer to exlcude; more precisely its AID
     */
    protected final void publishEvent(final Event event, final String argument,
        final AID playerToExclude) {
        // ----- Preconditions -----
        assert event != Event.NONE;
        // -------------------------
        
        // Create a 'Publish event' protocol initiator party.
        Party publishEventInitiator = ProtocolRegistry_StaticClass
            .getProtocol(Protocols.PUBLISH_EVENT_PROTOCOL)
            .createInitiatorParty(event, argument, playerToExclude);
        
        // Schedule the initiator party for execution.
        addBehaviour(publishEventInitiator);
    }
    
    // ----- PRIVATE -----
    
    // ----- Yellow pages management -----
    
    // TAG YELLOW-PAGES
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

    // TAG YELLOW-PAGES
    private OrganizationAgentDescription createOrganizationDescription() {
        return new OrganizationAgentDescription(getAID());
    }

    // TAG YELLOW-PAGES
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

    // TAG YELLOW-PAGES
    private RoleServiceDescription createRoleDescription(String roleName) {
        return new RoleServiceDescription(roleName);
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    // TAG YELLOW-PAGES
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

    // TAG YELLOW-PAGES
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

class PositionHolder {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The pseudo-random number generator.
     */
    private static final Random random = new Random();
    
    /**
     * The role positions.
     */
    private final Map<String, List<Role>> rolePositions = new HashMap<String, List<Role>>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    void addPosition(Role position) {
        List<Role> positions = rolePositions.get(position.getRoleName());
        
        // Add a new role-entry if necessary.
        if (positions == null) {
            positions = new ArrayList<Role>();
            rolePositions.put(position.getRoleName(), positions);
        }
        
        // Add the position.
        positions.add(position);
    }
    
    void removePosition(Role position) {
        List<Role> positions = rolePositions.get(position.getRoleName());
        
        // Remove the position.
        positions.remove(position);
        
        // Remove the old role entry if necessary.
        if (positions.isEmpty()) {
            rolePositions.remove(position.getRoleName());
        }
    }
    
    Role getActivePosition(String roleName) {
        return getFirstActivePosition(roleName);
    }
    
    List<Role> getAllActivePositions(String roleName) {
        List<Role> allPositions = rolePositions.get(roleName);
        List<Role> allActivePositions = new ArrayList<>();
        for (Role position : allPositions) {
            if (position.state == Role.RoleState.ACTIVE) {
                allActivePositions.add(position);
            }
        }
        return allActivePositions;
    }
    
    // ----- PRIVATE -----
    
    private Role getFirstActivePosition(String roleName) {
        List<Role> allActivePositions = getAllActivePositions(roleName);
        return (!allActivePositions.isEmpty()) ?
            allActivePositions.get(0) :
            null;
    }
    
    private Role getRandomActivePosition(String roleName) {
        List<Role> allActivePositions = getAllActivePositions(roleName);
        return (!allActivePositions.isEmpty()) ?
            allActivePositions.get(random.nextInt(allActivePositions.size())) :
            null;
    }
    
    // </editor-fold>
}