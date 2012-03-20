package thespian4jade.core.organization.kb;

import jade.core.AID;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * An organization knowledge base.
 * @author Lukáš Kúdela
 * @since 2011-10-27
 * @version %I% %G%
 */
public class OrganizationKnowledgeBase {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The pseudo-random number generator.
     */
    private static final Random random = new Random();
    
    /**
     * The enacted roles.
     * (Role name, Role AID) --> Player AID
     */
    private Map<String, Map<AID, AID>> enactedRoles = new HashMap<String, Map<AID, AID>>();
    
    /**
     * The enacting players.
     */
    private Map<AID, PlayerDescription> enactingPlayers = new HashMap<AID, PlayerDescription>();
    
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
        return getRoleInstance(roleName, playerAID) != null;
    }
    
    /**
     * Queries this organization knowledge base whether
     * a particular role is enacted by any player.
     * @param roleName the role name
     * @return <c>true</c> if the specified role is enacted by any player;
     *         <c>false</c> otherwise.
     */
    public boolean isRoleEnacted(String roleName) {
        return !getRoleInstances(roleName).isEmpty();
    }
    
    /**
     * Queries this organization knowledge base whether
     * a particular player enacts any role.
     * @param playerAID the player AID
     * @return <c>true</c> if the specified player enacts any role;
     *         <c>false</c> otherwise.
     */
    public boolean doesPlayerEnact(AID playerAID) {
        return enactingPlayers.containsKey(playerAID) &&
            enactingPlayers.get(playerAID).isEmployed();
    }
    
    /**
     * Gets all players enacting a role in the organization.
     * @return a set of all players enacting a role in the organization
     */
    public Set<AID> getAllPlayers() {
        return new HashSet(enactingPlayers.keySet());
    }
    
    /**
     * Gets the role instance of a specified role (class) for a specified player.
     * @param roleName the name of the role (class)
     * @param playerAID the AID of the player
     * @return the role instance of the specified role (class) for the cpecified player
     */
    public AID getRoleInstance(String roleName, AID playerAID) {
        Map<AID, AID> roleInstances = getRoleInstances(roleName);
        return (roleInstances.containsKey(playerAID)) ?
            roleInstances.get(playerAID) : null;
    }
    
    /**
     * Gets the first role instance of a specified role (class).
     * @param roleName the name of the role (class)
     * @return the first role instance of the specified role (class)
     */
    public AID getFirstRoleInstance(String roleName) {
        Map<AID, AID> roleInstances = getRoleInstances(roleName);
        if (!roleInstances.isEmpty()) {
            return getRoleInstanceAtIndex(roleInstances, 0);
        } else {
            return null;
        }      
    }
    
    /**
     * Gets a random role instance of a specified role (class).
     * @param roleName the name of the role (class)
     * @return a random role instance of the specified role (class)
     */
    public AID getRandomRoleInstance(String roleName) {
        Map<AID, AID> roleInstances = getRoleInstances(roleName);    
        if (!roleInstances.isEmpty()) {
            int roleInstanceIndex = random.nextInt(roleInstances.values().size());
            return getRoleInstanceAtIndex(roleInstances, roleInstanceIndex);
        } else {
            return null;
        }
    }
    
    /**
     * Gets all role instances of a specified role (class).
     * @param roleName the name of the role (class)
     * @return the set of all role instances of the specified role (class)
     */
    public Set<AID> getAllRoleInstances(String roleName) {
        Map<AID, AID> roleInstances = getRoleInstances(roleName);
        return new HashSet<AID>(roleInstances.values());
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
        assert roleName != null && !roleName.isEmpty();
        assert roleAID != null;
        assert playerAID != null;
        // -------------------------
        
        getRoleInstances(roleName).put(playerAID, roleAID);
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
        assert roleName != null && !roleName.isEmpty();
        assert playerAID != null;
        // -------------------------
        
        getRoleInstances(roleName).remove(playerAID);
        updatePlayerDeactsRole(playerAID, roleName);
    }
     
    // ---------- PRIVATE ----------

    private void updatePlayerEnactsRole(AID playerAID, String roleName) {
        // ----- Preconditions -----
        assert playerAID != null;
        assert roleName != null && !roleName.isEmpty();
        // ------------------------
        
        addPlayerIfApplicable(playerAID);
        getPlayerDescription(playerAID).enactRole(roleName);
    }

    private void updatePlayerDeactsRole(AID playerAID, String roleName) {
       // ----- Preconditions -----
        assert playerAID != null;
        assert roleName != null && !roleName.isEmpty();
        // ------------------------

        getPlayerDescription(playerAID).deactRole(roleName);
        removePlayerIfApplicable(playerAID);
    }
    
    private void addPlayerIfApplicable(AID playerAID) {
        if (!enactingPlayers.containsKey(playerAID)) {
            enactingPlayers.put(playerAID, new PlayerDescription(playerAID));
        }
    }
    
    private void removePlayerIfApplicable(AID playerAID) {
        if (!getPlayerDescription(playerAID).isEmployed()) {
            enactingPlayers.remove(playerAID);
        }
    }
    
    /**
     * Gets the player description for a specified player
     * @param playerAID the AID of the player
     * @return the player description for the specified player
     */
    private PlayerDescription getPlayerDescription(AID playerAID) {
        return enactingPlayers.get(playerAID);
    }
    
    /**
     * Gets the role instances of a specified role (class).
     * @param roleName the name of the role (class)
     * @return the role instances of the specified role (class)
     */
    private Map<AID, AID> getRoleInstances(String roleName) {
        if (!enactedRoles.containsKey(roleName)) {
            enactedRoles.put(roleName, new HashMap<AID, AID>());
        }
        return enactedRoles.get(roleName);
    }
    
    private AID getRoleInstanceAtIndex(Map<AID, AID> roleInstances, int index) {
        for (AID roleInstance : roleInstances.values()) {
            if (index == 0) {
                return roleInstance;
            }
            index--;
        }
        return null;
    }
    
    // </editor-fold> 
}
