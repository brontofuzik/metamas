package thespian4jade.core.organization.kb;

import jade.core.AID;
import java.util.HashSet;
import java.util.Set;
import thespian4jade.core.Event;

/**
 * A player description kept by an organization.
 * @author Lukáš Kúdela
 * @since 2011-10-18
 * @version %I% %G%
 */
public class PlayerDescription {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The player AID.
     */
    private AID playerAID;
    
    /**
     * The roles the player enacts.
     */
    private Set<String> enactedRoles = new HashSet<String>();
    
    /**
     * The events the player is subscribed to.
     */
    private Set<Event> subscribedEvents = new HashSet<Event>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instance of the PlayerDescription class.
     * @param playerAID the player AID
     */
    PlayerDescription(AID playerAID) {
        // ----- Preconditions -----
        assert playerAID != null;
        // -------------------------
        
        this.playerAID = playerAID;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the player AID.
     * @return the player AID
     */
    AID getAID() {
        return playerAID;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    // ----- Query -----
    
    /**
     * Determies whether the player enacts a given role.
     * @param role the role
     * @return <c>true</c> if the player enacts the given role,
     *     <c>false</c> otherwise
     */
    boolean doesEnactRole(String role) {
        return enactedRoles.contains(role); 
    }
    
    /**
     * Determines whether the player is employed, i.e. if it enacts some roles
     * in the organization.
     * @return <c>true</c> if the player is employed,
     *     <c>false</c> otherwise
     */
    boolean isEmployed() {
        return !enactedRoles.isEmpty();
    }
    
    /**
     * Determies whether the player is subscribed to a given role.
     * @param event the event
     * @return <c>true</c> if the player is subscribed to the given event,
     *     <c>false</c> otherwise
     */
    boolean isSubscribedToEvent(Event event) {
        return subscribedEvents.contains(event);
    }
    
    // ----- Update -----
    
    /**
     * Enacts a role.
     * @param role the role being enacted
     */
    void enactRole(String role) {
        // ----- Preconditions -----
        assert role != null && !role.isEmpty();
        assert !enactedRoles.contains(role);
        // -------------------------
        
        enactedRoles.add(role);
    }
    
    /**
     * Deacts a role.
     * @param role the role being deacted 
     */
    void deactRole(String role) {
        // ----- Preconditions -----
        assert role != null && !role.isEmpty();
        assert enactedRoles.contains(role);
        // -------------------------
        
        enactedRoles.remove(role);
    }
   
    /**
     * Subscribes to an event.
     * @param event the event being subscribed to
     */
    void subscribeToEvent(Event event) {
        // ----- Preconditions -----
        assert event != null && event != Event.NONE;
        // -------------------------
        
        subscribedEvents.add(event);
    }

    /**
     * Unsubscribes from an event.
     * @param event the event being unsubscribed from 
     */
    void unsubscribeFromEvent(Event event) {
        // ----- Preconditions -----
        assert event != null && event != Event.NONE;
        // -------------------------
        
        subscribedEvents.remove(event);
    }
    
    // </editor-fold> 
}
