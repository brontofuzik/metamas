package jadeorg.core.organization.kb;

import jade.core.AID;
import jadeorg.core.organization.Role;
import java.util.Hashtable;
import java.util.Map;

/**
 * An organization knowledge base.
 * @author Lukáš Kúdela
 * @since 2011-10-27
 * @version %I% %G%
 */
public class OrganizationKnowledgeBase {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The enacted roles. */
    private Map<String, AID> enactedRoles = new Hashtable<String, AID>();
    
    /** The players enacting roles. */
    private Map<AID, PlayerDescription> enactingPlayers = new Hashtable <AID, PlayerDescription>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
   
    // ----- QUERY -----
    
    /**
     * Queries this organization knowledge base to determine whether
     * a particular role is enacted by a particular player.
     * @param roleName the role name
     * @param playerAID the player AID
     * @return <c>true</c> if the specified role is enacted by the specified player;
     *         <c>false</c> otherwise.
     */
    public boolean isRoleEnactedByPlayer(String roleName, AID playerAID) {
        // TODO Implement.
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
        return enactedRoles.containsKey(roleName);
    }
    
    /**
     * Queries this organization knowledge base whether
     * a particular player enacts any role.
     * @param playerAID the player AID
     * @return <c>true</c> if the specified player enacts any role;
     *         <c>false</c> otherwise.
     */
    public boolean doesPlayerEnact(AID playerAID) {
        return enactingPlayers.get(playerAID).isEmployed();
    }
    
    public AID getRole(String roleName) {
        return enactedRoles.get(roleName);
    }
    
    // ----- UPDATE -----
    
    /**
     * Updates this organization knowledge base to contain information about
     * a particular role being enacted by a particular player.
     * @param roleName the role name
     * @param roleAID the role AID
     * @param playerAID  the player AID
     */
    public void updateRoleIsEnacted(String roleName, AID roleAID, AID playerAID) {
        // ----- Preconditions -----
        assert roleAID != null;
        assert playerAID != null;
        assert !enactedRoles.containsKey(roleName);
        // -------------------------
        
        enactedRoles.put(roleName, roleAID);
        updatePlayerEnactsRole(playerAID, roleName);
    }
    
    /**
     * Updates this organization knowledge base to contain information about
     * a particular role being deacted by a particular player.
     * @param roleName the role name
     * @param playerAID the player AID
     */
    public void updateRoleIsDeacted(String roleName, AID playerAID) {
        // ----- Preconditions -----
        assert roleName != null;
        assert playerAID != null;
        assert enactedRoles.containsKey(roleName);
        // -------------------------
        
        enactedRoles.remove(roleName);
        updatePlayerDeactsRole(playerAID, roleName);
    }
    
    // ---------- PRIVATE ----------

    private void updatePlayerEnactsRole(AID playerAID, String roleName) {
        // ----- Preconditions -----
        assert playerAID != null;
        assert roleName != null && !roleName.isEmpty();
        // ------------------------
        
        // Get the player info.
        PlayerDescription playerDescription = getPlayerDescription(playerAID);
        
        // Create the player info if the player is unemployed.
        if (!playerDescription.isEmployed()) {
            enactingPlayers.put(playerAID, playerDescription);
        }
        
        // Enact the role.
        playerDescription.enactRole(roleName);
    }

    private void updatePlayerDeactsRole(AID playerAID, String roleName) {
       // ----- Preconditions -----
        assert playerAID != null;
        assert roleName != null && !roleName.isEmpty();
        // ------------------------
        
        // Get the player info.
        PlayerDescription playerDescription = getPlayerDescription(playerAID);
        
        // Deact the role.
        playerDescription.deactRole(roleName);
        
        // Delete the player info if the player is unemployed.
        if (!playerDescription.isEmployed()) {
            enactingPlayers.remove(playerAID);
        }
    }
    
    private PlayerDescription getPlayerDescription(AID playerAID) {
        PlayerDescription playerDescription = enactingPlayers.get(playerAID);
        if (playerDescription == null) {
            playerDescription = new PlayerDescription(playerAID);
        }
        return playerDescription;
    }
    
    // </editor-fold>  
}
