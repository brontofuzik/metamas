package jadeorg.core;

import jade.core.AID;
import java.util.HashSet;
import java.util.Set;

/**
 * A player description.
 * @author Lukáš Kúdela
 * @since 2011-10-18
 * @version %I% %G%
 */
class PlayerDescription {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The AID of the player. */
    private AID playerAID;
    
    /** The name of the player. */
    private String name;
    
    /** The roles the player enacts. */
    private Set<String> enactedRoles = new HashSet<String>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PlayerDescription(AID playerAID) {
        // ----- Preconditions -----
        if (playerAID == null) {
            throw new NullPointerException("playerAID");
        }
        // -------------------------
        
        this.playerAID = playerAID;
        name = playerAID.getName();
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public AID getAID() {
        return playerAID;
    }
    
    public String getName() {
        return name;
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
        return enactedRoles.size() != 0;
    }
    
    // </editor-fold> 
}
