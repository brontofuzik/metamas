package jadeorg.core;

import jade.core.AID;
import java.util.HashSet;
import java.util.Set;

/**
 * A player info.
 * @author Lukáš Kúdela (2011-10-18)
 */
class PlayerInfo {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The AID of the player. */
    private AID playerAID;
    
    /** The name of the player. */
    private String name;
    
    /** The roles the player enacts. */
    private Set<String> enactedRoles = new HashSet<String>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public PlayerInfo(AID playerAID) {
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
    
    boolean doesEn
            
    void enactRole(String role) {
    }
    
    void deactRole(String role) {
    }
    
    // </editor-fold> 
}
