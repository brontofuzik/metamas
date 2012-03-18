package thespian4jade.core.organization;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.util.Logger;
import thespian4jade.proto.roleprotocol.invokeresponsibilityprotocol.InvokeResponsibilityProtocol;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * A role agent.
 * @author Lukáš Kúdela
 * @since 2011-10-16
 * @version %I% %G%
 */
public class Role extends Agent {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final List<String> responsibilities = new ArrayList<String>();
    
    Organization myOrganization;
    
    final Map<String, Class> competences = new Hashtable<String, Class>();
    
    RoleState state = RoleState.INACTIVE;
    
    AID playerAID;
    
    // ----- PRIVATE -----
  
    // TAG OBSOLETE
//    private Role_Initiator initiator = new Role_Initiator(this);
    
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
    
    // TAG OBSOLETE
//    public void invokeResponsibility(String responsibilityName, Object argument) {
//        initiator.initiateProtocol(InvokeResponsibilityProtocol.getInstance(),
//            new Object[] { responsibilityName, argument } );
//    }
    
    public void invokeResponsibility(String responsibilityName, Object argument) {
        addBehaviour(InvokeResponsibilityProtocol.getInstance()
            .createInitiatorParty(new Object[] { responsibilityName, argument }));    
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
    
    /**
     * Adds a responsibility.
     * @param responsibility the responsibility to add
     */
    protected static void addResponsibility(String responsibility) {
        // ----- Preconditions -----
        assert responsibility != null && !responsibility.isEmpty();
        // -------------------------
        
        responsibilities.add(responsibility);
    }
    
    /**
     * Gets the responsibilities (as an array)
     * @return the responsibilities (as an array)
     */
    protected static String[] getResponsibilities() {
        return responsibilities.toArray(new String[responsibilities.size()]);
    }
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add behaviours.
        addBehaviour(new Role_Responder());
        logInfo("Behaviours added.");
        
        // TAG YellowPages
        //registerWithYellowPages();
    }
    
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
    
    // TAG YellowPages
    private void registerWithYellowPages() { 
        try {
            DFService.register(this, createAgentDescription());
        } catch (FIPAException ex) {
            ex.printStackTrace();
        }
        logInfo("Registered with the Yellow Pages.");
    }
    
    // TAG YellowPages
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


