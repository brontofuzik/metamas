package jadeorg.core;

import jade.core.AID;
import java.util.Hashtable;
import java.util.Map;

/**
 * An organization knowledge base.
 * @author Lukáš Kúdela (2011-10-27)
 * @version 0.1
 */
class OrganizationKnowledgeBase {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The role classes. */
    private Map<String, Class> roleClasses = new Hashtable<String, Class>();
    
    /** The enacted roles. */
    private Map<String, Role> enactedRoles = new Hashtable<String, Role>();
    
    /** The players enacting roles. */
    public Hashtable <AID, PlayerInfo> players = new Hashtable <AID, PlayerInfo>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    // ----- QUERY METHODS -----
    
    /**
     * Queries this organization knowledge base to determine whether
     * a particular role is enacted by a particular player.
     * @param roleName the role name
     * @param player the player AID
     * @return <c>true</c> if the specified role is enacted by the specified player;
     *         <c>false</c> otherwise.
     */
    public boolean queryIfRoleIsEnactedByPlayer(String roleName, AID player) {
        return true;
    }
    
    /**
     * Queries this organization knowledge base whether
     * a particular role is enacted by any player.
     * @param roleName the role name
     * @return <c>true</c> if the specified role is enacted by any player;
     *         <c>false</c> otherwise.
     */
    public boolean queryIfRoleIeEnacted(String roleName) {
        return false;
    }
    
    /**
     * Queries this organization knowledge base whether
     * a particular player enacts any role.
     * @param player the player AID
     * @return <c>true</c> if the specified player enacts any role;
     *         <c>false</c> otherwise.
     */
    public boolean queryIfPlayerEnacts(AID player) {
        return false;
    }
    
    // ----- UPDATE METHODS -----
    
    /**
     * Updates this organization knowledge base to contain information about
     * a particular role being enacted by a particular player.
     * @param role the role
     * @param player 
     */
    void updateRoleIsEnacted(Role role, AID player) {
        // ----- Preconditions -----
        assert !enactedRoles.containsKey(role);
        // -------------------------
        
        enactedRoles.put(role.getName(), role);
        PlayerInfo playerInfo = getPlayerInfo(player);
        
    }
    
    /**
     * Updates this organization knowledge base to contain information about
     * a partuclar role being deacted by a particular player.
     * @param role the role 
     * @param player
     */
    void roleIsDeactedByPlayer(Role role, AID player) {
        // ----- Preconditions -----
        assert enactedRoles.containsKey(role);
        // -------------------------
        
        enactedRoles.remove(role.getName());
        removePlayer(player);
    }
    
    // ---------- PRIVATE ----------
    
    private PlayerInfo getPlayerInfo(AID playerAID) {
        PlayerInfo playerInfo = players.get(playerAID);
        if (playerInfo == null) {
            // A new player.
            playerInfo = new PlayerInfo(playerAID);
            players.put(playerAID, playerInfo);
        }
        return playerInfo;
    }
    
    private void addPlayer(AID player) {
        // Get the player info.
        PlayerInfo playerInfo = players.get(player);
        if (playerInfo == null) {
            // A new player.
            playerInfo = new PlayerInfo(player);
            players.put(player, playerInfo);
        }
        playerInfo
        
        
        players.put(player, new PlayerInfo(player));
    }
        
    // </editor-fold>  
}
