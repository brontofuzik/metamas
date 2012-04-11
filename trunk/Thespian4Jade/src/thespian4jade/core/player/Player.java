package thespian4jade.core.player;

import thespian4jade.core.player.kb.PlayerKnowledgeBase;
import jade.core.Agent;
import jade.core.behaviours.WakerBehaviour;
import jade.util.Logger;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import thespian4jade.asynchrony.Future;
import thespian4jade.core.Event;
import thespian4jade.behaviours.parties.Party;
import thespian4jade.protocols.ProtocolRegistry;
import thespian4jade.protocols.Protocols;

/**
 * A player.
 * The class represents a player type and its instances represent the player tokens.
 * @author Lukáš Kúdela
 * @since 2011-10-17
 * @version %I% %G%
 */
public abstract class Player extends Agent {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player type's responsibilities.
     */
    final Map<String, Class> responsibilities = new HashMap<String, Class>();
    
    /**
     * The player's event handlers.
     */
    final Map<Event, Class> eventHandlers = new HashMap<Event, Class>();
    
    /**
     * The knowledge base.
     */
    final PlayerKnowledgeBase knowledgeBase = new PlayerKnowledgeBase();
    
    // ----- PRIVATE -----
    
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
     * @return <c>true</c> if all responsibilities can be met,
     *     <c>false</c> otherwise
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
        Party enactRoleInitiator = ProtocolRegistry
            .getProtocol(Protocols.ENACT_ROLE_PROTOCOL)
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
        Party deactRoleInitiator = ProtocolRegistry
            .getProtocol(Protocols.DEACT_ROLE_PROTOCOL)
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
        Party activateRoleInitiator = ProtocolRegistry
            .getProtocol(Protocols.ACTIVATE_ROLE_PROTOCOL)
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
        Party deactivateRoleInitiator = ProtocolRegistry
            .getProtocol(Protocols.DEACTIVATE_ROLE_PROTOCOL)
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
        Party invokeCompetenceInitiator = ProtocolRegistry
            .getProtocol(Protocols.INVOKE_COMPETENCE_PROTOCOL)
            .createInitiatorParty(competenceName, argument);
        
        // Get the inititor party result future.
        Future<TResult> future = ((Player_InvokeCompetence_InitiatorParty)
            invokeCompetenceInitiator).getResultFuture();
        
        // Schedule the initiator party for execution.
        addBehaviour(invokeCompetenceInitiator);
        
        return future;
    }
    
    /**
     * Subscribes to an event.
     * @param organizationName the name of the organization
     * @param event the event to subscribe to
     * @param eventHandler the event handler
     */
    public final void subscribeToEvent(String organizationName, Event event,
        Class eventHandler) {
        // Create a 'Subscribe to event' protocol initiator party.
        Party subscribeToEventInitiator = ProtocolRegistry
            .getProtocol(Protocols.SUBSCRIBE_TO_EVENT_PROTOCOL)
            .createInitiatorParty(organizationName, event, eventHandler);
        
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
     * Logs a message at the INFO level.
     * @param message the message to log
     */
    public void logInfo(String message) {
        log(Level.INFO, message);
    }
    
    /**
     * Logs a SEVERE-level message.
     * @param message 
     */
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
     * @return <c>true</c> if all responsibility can be met,
     *     <c>false</c> otherwise
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
     * @return <c>true</c> if any responsibility can be met,
     *     <c>false</c> otherwise
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
     * @return <c>true</c> if all responsibilities can be met,
     *     <c>false</c> otherwise 
     */
    protected boolean evaluateReponsibility(String responsibility) {
        return responsibilities.containsKey(responsibility);
    }
    
    /**
     * Adds an event handler.
     * @param event the event the handler handles
     * @param eventHandlerClass the event handler class
     */
    protected final void addEventHandler(Event event, Class eventHandlerClass) {
        // ----- Preconditions -----
        if (event == null || event == Event.NONE) {
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
    protected final void removeEventHandler(Event event) {
        // ----- Preconditions -----
        if (event == null || event == Event.NONE) {
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
     * @param timeout the timeout of execution
     */
    protected final void scheduleEnactRole(final RoleFullName roleFullName, final int timeout) {
        addBehaviour(new WakerBehaviour(this, timeout) {    
            @Override
            protected void handleElapsedTimeout() {
                ((Player)myAgent).enactRole(roleFullName.getOrganizationName(),
                    roleFullName.getRoleName());
            }
        });
    }
    
    /**
     * Schedules a role deactment.
     * Takes 2 seconds.
     * @param roleFullName the full name of the role to deact
     * @param timeout the timeout of execution
     */
    protected final void scheduleDeactRole(final RoleFullName roleFullName,
        final int timeout) {
        addBehaviour(new WakerBehaviour(this, timeout) {
            @Override
            protected void handleElapsedTimeout() {
                ((Player)myAgent).deactRole(roleFullName.getOrganizationName(),
                    roleFullName.getRoleName());
            }
        });
    }
    
    /**
     * Schedules a role activation.
     * Takes 2 seconds.
     * @param roleFullName the full name of the role to actiavte
     * @param timeout the timeout of execution
     */
    protected final void scheduleActivateRole(final RoleFullName roleFullName,
        final int timeout) {
        addBehaviour(new WakerBehaviour(this, timeout) {            
            @Override
            protected void handleElapsedTimeout() {
                ((Player)myAgent).activateRole(roleFullName.getRoleName());
            }
        });
    }
    
    /**
     * Schedules a role deactivation.
     * @param roleFullName the full name of the role to deactivate
     * @param timeout the timeout of execution
     */
    protected final void scheduleDeactivateRole(final RoleFullName roleFullName,
        final int timeout) {
        addBehaviour(new WakerBehaviour(this, timeout) {
            @Override
            protected void handleElapsedTimeout() {
                ((Player)myAgent).deactivateRole(roleFullName.getRoleName());
            }
        });
    }
        
    /**
     * Schedules a competence invocation.
     * @param competenceName the name of the competence to invoke
     * @param argument the competence argument
     * @param timeout the timeout of execution
     */
    protected final <TArgument extends Serializable> void scheduleInvokeCompetence(
        final String competenceName, final TArgument argument,
        final int timeout) {
        addBehaviour(new WakerBehaviour(this, timeout) {
            @Override
            protected void handleElapsedTimeout() {
                ((Player)myAgent).invokeCompetence(competenceName, argument);
            }
        });
    }
    
    /**
     * Schedules an event subscription.
     * @param organizationName the name of the organization
     * @param event the event
     * @param eventHandlerClass the event handler class
     * @param timeout the timeout of execution
     */
    protected final void scheduleSubscribeToEvent(final String organizationName,
        final Event event, final Class eventHandlerClass, final int timeout) {
        addBehaviour(new WakerBehaviour(this, timeout) {    
            @Override
            protected void handleElapsedTimeout() {
                ((Player)myAgent).subscribeToEvent(organizationName, event, eventHandlerClass);
            }
        });
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * A full role name is the name of the role qualified with the name of
     * the organization.
     */
    protected static class RoleFullName {
        
        // <editor-fold defaultstate="collapsed" desc="Fields">
        
        /**
         * The name of organization.
         */
        private String organizationName;
        
        /**
         * The name of the role.
         */
        private String roleName;
        
        // </editor-fold>
        
        // <editor-fold defaultstate="collapsed" desc="Constructors">
        
        /**
         * Initializes a new instance of the RoleFullName class.
         * @param organizationName the name of the organziation
         * @param roleName the name of the role
         */
        public RoleFullName(String organizationName, String roleName) {
            this.organizationName = organizationName;
            this.roleName = roleName;
        }
            
        /**
         * Initializes a new instance of the RoleFullName class.
         * @param roleFullName the string representing the full name of the role
         */
        public RoleFullName(String roleFullName) {
            String[] nameParts = roleFullName.split("\\.");
            organizationName = nameParts[0];
            roleName = nameParts[1];
        }
        
        // </editor-fold>  
        
        // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
        
        /**
         * Gets the name of the organization.
         * @return the name of the organization
         */
        public String getOrganizationName() {
            return organizationName;
        }
        
        /**
         * Gets the name of the role.
         * @return the name of the role
         */
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
