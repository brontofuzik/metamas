package thespian4jade.core.organization;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import thespian4jade.asynchrony.Future;
import thespian4jade.behaviours.parties.Party;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;

/**
 * A role agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public class Role extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The role' competences.
     */
    final Map<String, Class> competences = new Hashtable<String, Class>();
    
    /**
     * The position's organization.
     */
    Organization myOrganization;

    /**
     * The position's enacting player; more precisely its AID.
     */
    AID enactingPlayer;
    
    /**
     * The position's state.
     */
    RoleState state = RoleState.INACTIVE;
    
    // ----- PRIVATE -----
    
    /**
     * The logger.
     */
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    /**
     * Initializes a new instance of the Role class.
     */
    public Role() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Enums">
    
    /**
     * Role states.
     */
    enum RoleState {
        INACTIVE,
        ACTIVE,
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the name of the role.
     * @return the name of the role 
     */
    public String getRoleName() {
        return getClass().getSimpleName();
    }
    
    /**
     * Gets the containing organization
     * @return the containing organization
     */
    public Organization getMyOrganization() {
        return myOrganization;
    }
    
    /**
     * Sets the containing organizaiton.
     * @param organization the containing organization
     */
    public void setMyOrganization(Organization organization) {
        // ----- Preconditions -----
        assert organization != null;
        // -------------------------
        
        this.myOrganization = organization;
    }
    
    /**
     * Gets the enacting player; more precisely, its AID.
     * @return the enacting player; more precisely, its AID
     */
    public AID getEnactingPlayer() {
        return enactingPlayer;
    }
    
    /**
     * Sets the enacting player; more preciselt its AID.
     * @param player the enacting player; more precisely its AID
     */
    public void setEnactingPlayer(AID player) {
        this.enactingPlayer = player;
    }
    
    // ----- PACKAGE -----
    
    /**
     * Gets the nickname.
     * A nickname is a concatenation of the role name and the name of the
     * enacting player.
     * @return the nickname
     */
    String getNickname() {
        // ----- Preconditions -----
        if (getEnactingPlayer() == null) {
            throw new IllegalStateException(String.format(
                "The role agent '%1$s' is not associated with a player.",
                getName()));
        }
        // -------------------------
        
        String roleAgentName = getRoleName().substring(0, 1).toLowerCase()
            + getRoleName().substring(1);
        String playerAgentName = getEnactingPlayer().getLocalName();
        return String.format("%1$s_%2$s", roleAgentName, playerAgentName);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Invokes a responsibility.
     * @param <TArgument> the type of the responsibility argument
     * @param <TResult> the type of the responsibility result
     * @param responsibilityName the name of the responsibility
     * @param argument the responsibility argument
     * @return the responsibility result future
     */
    public final <TArgument extends Serializable, TResult extends Serializable>
        Future<TResult> invokeResponsibility(String responsibilityName, TArgument argument) {
        // Create an 'Invoke responsibility' protocol initiator party.
        Party invokeResponsibilityInitiator = ProtocolRegistry
            .getProtocol(Protocols.INVOKE_RESPONSIBILITY_PROTOCOL)
            .createInitiatorParty(responsibilityName, argument);
        
        // Get the inititor party result future.
        Future<TResult> future = ((Role_InvokeResponsibility_InitiatorParty)
            invokeResponsibilityInitiator).getResultFuture();
        
        // Schedule the initiator party for execution.
        addBehaviour(invokeResponsibilityInitiator);
        
        return future;  
    }
    
    // ----- Logging -----
    
    /**
     * Logs a message.
     * @param level the level
     * @param message the message to log
     */
    public void log(Level level, String message) {
        if (logger.isLoggable(level)) {
            logger.log(level, String.format("%1$s: %2$s", getLocalName(), message));
        }
    }
    
    /**
     * Logs a message at the INFO level.
     * @param message the message to log
     */
    public void logInfo(String message) {
        log(Level.INFO, message);
    }
    
    // ----- PACKAGE -----
    
    /**
     * Activates the role.
     */
    void activate() {
        state = RoleState.ACTIVE;
    }
    
    /**
     * Deactivates the role.
     */
    void deactivate() {
        state = RoleState.INACTIVE;
    }
    
    /**
     * Determines if the role can be activated.
     * @return <c>true<c/> if the role can be activated, <c>false</c> otherwise
     */
    boolean isActivable() {
        return state == RoleState.INACTIVE;
    }
    
    /**
     * Determines if the role can be deactivated.
     * @return <c>true<c/> if the role can be deactivated, <c>false</c> otherwise
     */
    boolean isDeactivable() {
        return state == RoleState.ACTIVE;
    }
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add behaviours.
        addBehaviour(new Role_Responder());
        logInfo("Behaviours added.");
        
        // TAG YELLOW-PAGES
        //registerWithYellowPages();
    }
    
    /**
     * Adds a competence.
     * @param competenceClass the competence class 
     */
    protected void addCompetence(Class competenceClass) {
        // ----- Preconditions -----
        if (competenceClass == null) {
            throw new IllegalArgumentException("competenceClass");
        }
        // -------------------------
        
        String competenceName = competenceClass.getSimpleName();
        competences.put(competenceName, competenceClass);
        
        logInfo(String.format("Competence (%1$s) added.", competenceName));
    }
    
    /**
     * Schedules a responsibility invocation.
     * @param competenceName the name of the responsibility to invoke
     * @param argument the responsibility argument
     * @param timeout the timeout of execution
     */
    protected final <TArgument extends Serializable> void scheduleInvokeResponsibility(
        final String responsibilityName, final TArgument argument,
        final int timeout) {
        addBehaviour(new WakerBehaviour(this, timeout) {
            @Override
            protected void handleElapsedTimeout() {
                ((Role)myAgent).invokeResponsibility(responsibilityName, argument);
            }
        });
    }
    
    // ----- Yellow pages registration -----
    
    /**
     * TAG YELLOW-PAGES
     * Registers the role with the Yellow pages.
     * Note: This method is currently not used.
     */
    private void registerWithYellowPages() { 
        try {
            DFService.register(this, createRoleDescription());
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        logInfo("Registered with the Yellow Pages.");
    }
    
    /**
     * TAG YELLOW-PAGES
     * Creates the role description for the DF.
     * Note: This method is currently not used.
     * @return the role description
     */
    private DFAgentDescription createRoleDescription() {
        // Create the agent description.
        DFAgentDescription agentDescription = new DFAgentDescription();
        agentDescription.setName(getAID());
        
        // Create the service description.
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("TODO");
        serviceDescription.setName("TODO");
        agentDescription.addServices(serviceDescription);
        
        return agentDescription;
    }
    
    // </editor-fold>
}


