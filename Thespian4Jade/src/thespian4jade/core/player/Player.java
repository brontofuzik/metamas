package thespian4jade.core.player;

import thespian4jade.core.player.kb.PlayerKnowledgeBase;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.util.Logger;
import java.io.Serializable;
import thespian4jade.proto.organizationprotocol.deactroleprotocol.DeactRoleProtocol;
import thespian4jade.proto.organizationprotocol.enactroleprotocol.EnactRoleProtocol;
import thespian4jade.proto.roleprotocol.activateroleprotocol.ActivateRoleProtocol;
import thespian4jade.proto.roleprotocol.deactivateroleprotocol.DeactivateRoleProtocol;
import thespian4jade.proto.roleprotocol.invokecompetenceprotocol.InvokeCompetenceProtocol;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import thespian4jade.concurrency.Future;
import thespian4jade.proto.IResultParty;
import thespian4jade.proto.Party;
import thespian4jade.proto.organizationprotocol.subscribetoeventprotocol.SubscribeToEventProtocol;

/**
 * A player agent.
 * @author Lukáš Kúdela
 * @since 2011-10-17
 * @version %I% %G%
 */
public abstract class Player extends Agent {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The responsibilities (classes). */
    Map<String, Class> responsibilities = new HashMap<String, Class>();
    
    /** The event handlers (classes). */
    Map<String, Class> eventHandlers = new HashMap<String, Class>();
    
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
    
    /**
     * Enacts a role.
     * @param organizationName the name of the organization
     * @param roleName the name of the role to enact
     */
    public final void enactRole(final String organizationName,
        final String roleName) {
        // Create an 'Enact role' protocol initiator party.
        Party enactRoleInitiator = EnactRoleProtocol.getInstance()
            .createInitiatorParty(organizationName, roleName);
        
        // Schedule the initiator party for execution.
        addBehaviour(enactRoleInitiator);
    }
    
    /**
     * Deacts a role.
     * @param organizationName the name of the organization
     * @param roleName the name of the role to deact
     */
    public final void deactRole(final String organizationName,
        final String roleName) {
        // Create a 'Deact role' protocol initiator party.
        Party deactRoleInitiator = DeactRoleProtocol.getInstance()
            .createInitiatorParty(organizationName, roleName);
        
        // Schedule the initiator party for execution.
        addBehaviour(deactRoleInitiator);
    }
    
    /**
     * Activates a role.
     * @param roleName the name of the role to activate
     */
    public final void activateRole(final String roleName) {
        // Create a 'Activate role' potocol initiator party.
        Party activateRoleInitiator = ActivateRoleProtocol.getInstance()
            .createInitiatorParty(roleName);
        
        // Schedule the initiator party for execution.
        addBehaviour(activateRoleInitiator);
    }
       
    /**
     * Deactivates a role.
     * @param roleName the name of the role to deactivate
     */
    public final void deactivateRole(final String roleName) {
        // Create a 'Deactivate role' protocol initiator party.
        Party deactivateRoleInitiator = DeactivateRoleProtocol.getInstance()
            .createInitiatorParty(roleName);
        
        // Schedule the initiator party for execution.
        addBehaviour(deactivateRoleInitiator);
    }
    
    /**
     * Invokes a competence.
     * @param competenceName the name of the competence to invoke
     * @param argument the competence argument
     */
    public final <TArgument extends Serializable, TResult extends Serializable>
        Future<TResult> invokeCompetence(final String competenceName, final TArgument argument) {
        // Create an 'Invoke competence' protocol initiator party.
        Party invokeCompetenceInitiator = InvokeCompetenceProtocol.getInstance()
            .createInitiatorParty(competenceName, argument);
        
        // Get the inititor party result future.
        Future<TResult> future = ((IResultParty)invokeCompetenceInitiator).getResultFuture();
        
        // Schedule the initiator party for execution.
        addBehaviour(invokeCompetenceInitiator);
        
        return future;
    }
    
    public final void subscribeToEvent(String organizationName, String event) {
        // Create a 'Subscribe to event' protocol initiator party.
        Party subscribeToEventInitiator = SubscribeToEventProtocol.getInstance()
            .createInitiatorParty(organizationName, event);
        
        // Schedule the initiator party for execution.
        addBehaviour(subscribeToEventInitiator);
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
    
    // ----- PROTECTED -----
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add behaviours.
        addBehaviour(new Player_Responder());
        
        // LOG
        logInfo("Behaviours added.");
    }
    
    /**
     * Adds a responsibility.
     * @param responsibilityClass the responsibility class
     */
    protected final void addResponsibility(Class responsibilityClass) {
        // ----- Preconditions -----
        if (responsibilityClass == null) {
            throw new IllegalArgumentException("responsibilityClass");
        }
        // -------------------------
        
        String responsibilityName = responsibilityClass.getSimpleName();
        responsibilities.put(responsibilityName, responsibilityClass);
        
        // LOG
        logInfo(String.format("Responsibility (%1$s) added.", responsibilityName));
    }
    
    /**
     * Evaluates a set of responsibilities.
     * @param responsibilities the set of responsibilities to evaluate
     * @return <c>true</c> if all responsibility can be met; <c>false</c> otherwise
     */
    protected final boolean evaluateAllResponsibilities(String[] responsibilities) {
        for (String responsibility : responsibilities) {
            if (!evaluateReponsibility(responsibility)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Evaluates a set of responsibilities.
     * @param responsibilities the set of responsibilities to evaluate
     * @return <c>true</c> if any responsibility can be met; <c>false</c> otherwise
     */
    protected final boolean evaluteAnyResponsibilities(String[] responsibilities) {
        for (String responsibility : responsibilities) {
            if (evaluateReponsibility(responsibility)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Evaluates a responsibility.
     * @param responsibility the responsibility to evaluate
     * @return <c>true</c> if all responsibilities can be met; <c>false</c> otherwise 
     */
    protected boolean evaluateReponsibility(String responsibility) {
        return responsibilities.containsKey(responsibility);
    }
    
    /**
     * Adds an event handler.
     * @param event the event the handler handles
     * @param eventHandlerClass the event handler class
     */
    protected final void addEventHandler(String event, Class eventHandlerClass) {
        // ----- Preconditions -----
        if (event == null || event.isEmpty()) {
            throw new IllegalArgumentException("event");
        }
        if (eventHandlerClass == null) {
            throw new IllegalArgumentException("eventHandlerClass");
        }
        // -------------------------
        
        eventHandlers.put(event, eventHandlerClass);
        
        // LOG
        logInfo(String.format("Event handler (%1$s) added.", event));
    }
    
    /**
     * Removes an event handler.
     * @param event the event the handler handles
     */
    protected final void removeEventHandler(String event) {
        // ----- Preconditions -----
        if (event == null || event.isEmpty()) {
            throw new IllegalArgumentException("event");
        }
        // -------------------------
        
        eventHandlers.remove(event);
        
        // LOG
        logInfo(String.format("Event handler (%1$s) removed.", event));
    }
    
    // ----- Scheduling -----
    
    /**
     * Schedules a role enactment.
     * Takes 2 seconds.
     * @param roleFullName the full name of the role to enact
     * @param timeout the timeout
     */
    protected final void scheduleEnactRole(final RoleFullName roleFullName, final int timeout) {
        // Initiate the 'Enact role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout) {    
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().enactRole(roleFullName.getOrganizationName(),
                    roleFullName.getRoleName());
            }
        });
    }
    
    /**
     * Schedules a role deactment.
     * Takes 2 seconds.
     * @param roleFullName the full name of the role to deact
     * @param timeout the timeout
     */
    protected final void scheduleDeactRole(final RoleFullName roleFullName,
        final int timeout) {
        // Initiate the 'Deact role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout) {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().deactRole(roleFullName.getOrganizationName(),
                    roleFullName.getRoleName());
            }
        });
    }
    
    /**
     * Schedules a role activation.
     * Takes 2 seconds.
     * @param roleFullName the full name of the role to actiavte
     * @param timeout the timeout
     */
    protected final void scheduleActivateRole(final RoleFullName roleFullName,
        final int timeout) {
        // Initiate the 'Activate role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout) {            
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().activateRole(roleFullName.getRoleName());
            }
        });
    }
    
    /**
     * Schedules a role deactivation.
     * @param roleFullName the full name of the role to deactivate
     * @param timeout the timeout
     */
    protected final void scheduleDeactivateRole(final RoleFullName roleFullName,
        final int timeout) {
        // Initiate the 'Deactivate role' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout) {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().deactivateRole(roleFullName.getRoleName());
            }
        });
    }
        
    /**
     * Schedules a competence invocation.
     * @param competenceName the name of the competence to invoke
     * @param argument the competence argument
     * @param timeout the timeout
     */
    protected final <TArgument extends Serializable> void scheduleInvokeCompetence(
        final String competenceName, final TArgument argument,
        final int timeout) {
        // Initiate the 'Invoke competence' protocol.
        addBehaviour(new PlayerWakerBehaviour(this, timeout) {
            @Override
            protected void handleElapsedTimeout() {
                getMyPlayer().invokeCompetence(competenceName, argument);
            }
        });
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
        
        // <editor-fold defaultstate="collapsed" desc="Methods">
        
        public String toString() {
            //return "RoleFullName{" + "organizationName=" + organizationName + ", roleName=" + roleName + '}';
            return organizationName + "." + roleName;
        }
        
        // </editor-fold>
    }
    
    // </editor-fold>
}
