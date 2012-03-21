package thespian4jade.core.organization;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import java.io.Serializable;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityProtocol;
import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;
import thespian4jade.concurrency.Future;
import thespian4jade.proto.IResultParty;
import thespian4jade.proto.Party;

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
     * Design-time state.
     */
    final Map<String, Class> competences = new Hashtable<String, Class>();
    
    /**
     * The position's organization.
     * Run-time state.
     */
    Organization myOrganization;

    /**
     * The position's player; more precisely its AID.
     * Run-time state.
     */
    AID playerAID;
    
    /**
     * The position's state.
     * Run-time state.
     */
    RoleState state = RoleState.INACTIVE;
    
    // ----- PRIVATE -----
    
    /**
     * The logger.
     */
    private Logger logger;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Role() {
        logger = jade.util.Logger.getMyLogger(this.getClass().getName());
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Enums">
    
    enum RoleState {
        INACTIVE,
        ACTIVE,
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    // TODO (priority: high) Employ.
    public String getRoleName() {
        return getClass().getSimpleName();
    }
    
    /**
     * Gets my organization
     * @return my organization
     */
    public Organization getMyOrganization() {
        return myOrganization;
    }
    
    /**
     * Sets my organizaiton.
     * @param organization my organization
     */
    public void setMyOrganization(Organization organization) {
        // ----- Preconditions -----
        assert organization != null;
        // -------------------------
        
        this.myOrganization = organization;
    }
    
    public AID getPlayerAID() {
        return playerAID;
    }
    
    public void setPlayerAID(AID playerAID) {
        this.playerAID = playerAID;
    }
    
    // ----- PACKAGE -----
    
    String getNickname() {
        // ----- Preconditions -----
        if (getPlayerAID() == null) {
            throw new IllegalStateException(String.format(
                "The role agent '%1$s' is not associated with a player.",
                getName()));
        }
        // -------------------------
        
        String roleAgentName = getClass().getSimpleName().substring(0, 1).toLowerCase()
            + getClass().getSimpleName().substring(1);
        String playerAgentName = getPlayerAID().getLocalName();
        return String.format("%1$s_%2$s", roleAgentName, playerAgentName);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public final <TArgument extends Serializable, TResult extends Serializable>
        Future<TResult> invokeResponsibility(String responsibilityName, TArgument argument) {
        // Create an 'Invoke responsibility' protocol initiator party.
        Party invokeResponsibilityInitiator = InvokeResponsibilityProtocol.getInstance()
            .createInitiatorParty(responsibilityName, argument);
        
        // Get the inititor party result future.
        Future<TResult> future = ((IResultParty)invokeResponsibilityInitiator).getResultFuture();
        
        // Schedule the initiator party for execution.
        addBehaviour(invokeResponsibilityInitiator);
        
        return future;  
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
    
    // ----- PACKAGE -----
    
    void activate() {
        state = RoleState.ACTIVE;
    }
    
    void deactivate() {
        state = RoleState.INACTIVE;
    }
    
    boolean isActivable() {
        return state == RoleState.INACTIVE;
    }
    
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
     * Design-time behaviour.
     * @param competenceClass 
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
    
    // ----- Yellow pages registration -----
    
    // TAG YELLOW-PAGES
    private void registerWithYellowPages() { 
        try {
            DFService.register(this, createAgentDescription());
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        logInfo("Registered with the Yellow Pages.");
    }
    
    // TAG YELLOW-PAGES
    private DFAgentDescription createAgentDescription() {
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


