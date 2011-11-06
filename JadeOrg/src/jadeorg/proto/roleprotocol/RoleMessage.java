/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jadeorg.proto.roleprotocol;

import jade.core.AID;
import jadeorg.lang.Message;

/**
 * A 'Role' message.
 * An 'Role' message is a message exchanged between aa role and a player
 * containing a request to activate/deactivate a role.
 * @author Lukáš Kúdela
 * @since 2011-11-06
 * @version %I% %G%
 */
public abstract class RoleMessage extends Message {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The role AID. */
    private AID roleAID;
    
    /** The player AID. */
    private AID playerAID;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    /**
     * Gets the role AID.
     * @return the role AID
     */
    public AID getRole() {
        return roleAID;
    }
    
    /**
     * Sets the role AID.
     * DP: Fluent interface
     * @param playerAID the role AID
     * @return this 'Role' message
     */
    public RoleMessage setRole(AID roleAID) {
        // ----- Preconditions -----
        assert roleAID != null;
        // -------------------------
        
        this.roleAID = roleAID;
        return this;
    }
    
    /**
     * Gets the player AID.
     * @return the player AID
     */
    public AID getPlayer() {
        return playerAID;
    }
    
    /**
     * Sets the player AID.
     * DP: Fluent interface
     * @param playerAID the player AID
     * @return this 'Role' message
     */
    public RoleMessage setPlayer(AID playerAID) {
        // ----- Preconditions -----
        assert playerAID != null;
        // -------------------------
        
        this.playerAID = playerAID;
        return this;
    }
    
    // </editor-fold>
}
