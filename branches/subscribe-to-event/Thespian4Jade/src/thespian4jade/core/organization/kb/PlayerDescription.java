package thespian4jade.core.organization.kb;

import jade.core.AID;
import java.util.HashSet;
import java.util.Set;
import thespian4jade.core.Event;

/**
 * A player description for an organization.
 * @author Lukáš Kúdela
 * @since 2011-10-18
 * @version %I% %G%
 */
public class PlayerDescription {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The AID of the player. */
    private AID playerAID;
    
    /** The roles the player enacts. */
    private Set<String> enactedRoles = new HashSet<String>();
    
    private Set<Event> subscribedEvents = new HashSet<Event>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    PlayerDescription(AID playerAID) {
        // ----- Preconditions -----
        if (playerAID == null) {
            throw new NullPointerException("playerAID");
        }
        // -------------------------
        
        this.playerAID = playerAID;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    AID getAID() {
        return playerAID;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
            
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
     * Determines whether the player is employed by the organization
     * owning this player info.
     * @return <c>true</c> if the player is employed; <c>false</c> otherwise.
     */
    boolean isEmployed() {
        return !enactedRoles.isEmpty();
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
