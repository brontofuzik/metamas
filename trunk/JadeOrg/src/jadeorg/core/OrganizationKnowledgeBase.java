package jadeorg.core;

import jade.core.AID;
import java.util.Hashtable;
import java.util.Map;

/**
 * An organization knowledge base.
 * @author Lukáš Kúdela
 * @since 2011-10-27
 * @version %I% %G%
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
    
    // ----- QUERY -----
    
    /**
     * Queries this organization knowledge base to determine whether
     * a particular role is enacted by a particular player.
     * @param roleName the role name
     * @param player the player AID
     * @return <c>true</c> if the specified role is enacted by the specified player;
     *         <c>false</c> otherwise.
     */
    public boolean isRoleEnactedByPlayer(String roleName, AID player) {
        return true;
    }
    
    /**
     * Queries this organization knowledge base whether
     * a particular role is enacted by any player.
     * @param roleName the role name
     * @return <c>true</c> if the specified role is enacted by any player;
     *         <c>false</c> otherwise.
     */
    public boolean isRoleEnacted(String roleName) {
        return false;
    }
    
    /**
     * Queries this organization knowledge base whether
     * a particular player enacts any role.
     * @param player the player AID
     * @return <c>true</c> if the specified player enacts any role;
     *         <c>false</c> otherwise.
     */
    public boolean doesPlayerEnact(AID player) {
        return false;
    }
    
    // ----- UPDATE -----
    
    /**
     * Updates this organization knowledge base to contain information about
     * a particular role being enacted by a particular player.
     * @param role the role
     * @param player 
     */
    void updateRoleIsEnacted(Role role, AID player) {
        // ----- Preconditions -----
        assert role != null;
        assert player != null;
        assert !enactedRoles.containsKey(role);
        // -------------------------
        
        enactedRoles.put(role.getName(), role);
        updatePlayerEnactsRole(player, role.getName());
    }
    
    /**
     * Updates this organization knowledge base to contain information about
     * a partuclar role being deacted by a particular player.
     * @param role the role 
     * @param player
     */
    void roleIsDeactedByPlayer(Role role, AID player) {
        // ----- Preconditions -----
        assert role != null;
        assert player != null;
        assert enactedRoles.containsKey(role);
        // -------------------------
        
        enactedRoles.remove(role.getName());
        updatePlayerDeactsRole(player, role.getName());
    }
    
    // ---------- PRIVATE ----------

    private void updatePlayerEnactsRole(AID playerAID, String roleName) {
        // ----- Prconditions -----
        assert playerAID != null;
        assert roleName != null && !roleName.isEmpty();
        // ------------------------
        
        // Get the player info.
        PlayerInfo playerInfo = getPlayerInfo(playerAID);
        
        // Create the player info if the player is unemployed.
        if (!playerInfo.isEmployed()) {
            players.put(playerAID, playerInfo);
        }
        
        // Enact the role.
        playerInfo.enactRole(roleName);
    }

    private void updatePlayerDeactsRole(AID playerAID, String roleName) {
       // ----- Prconditions -----
        assert playerAID != null;
        assert roleName != null && !roleName.isEmpty();
        // ------------------------
        
        // Get the player info.
        PlayerInfo playerInfo = getPlayerInfo(playerAID);
        
        // Deact the role.
        playerInfo.deactRole(roleName);
        
        // Delete the player info if the player is unemployed.
        if (!playerInfo.isEmployed()) {
            players.remove(playerAID);
        }
    }
    
    private PlayerInfo getPlayerInfo(AID playerAID) {
        PlayerInfo playerInfo = players.get(playerAID);
        if (playerInfo == null) {
            playerInfo = new PlayerInfo(playerAID);
        }
        return playerInfo;
    }
    
    // </editor-fold>  
}
