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
   
    // ----- QUERY: ROLES -----
    
    /**
     * Queries this organization knowledge base to determine whether
     * a particular role is enacted by a particular player.
     * @param roleName the role name
     * @param playerAID the player; more precisely its AID
     * @return <c>true</c> if the specified role is enacted by the specified player;
     *         <c>false</c> otherwise.
     */
    public boolean isRoleEnactedByPlayer(String roleName, AID player) {
        return getPosition(roleName, player) != null;
    }
    
    /**
     * Queries this organization knowledge base whether
     * a particular role is enacted by any player.
     * @param roleName the role name
     * @return <c>true</c> if the specified role is enacted by any player;
     *         <c>false</c> otherwise.
     */
    public boolean isRoleEnacted(String roleName) {
        return !getPositions(roleName).isEmpty();
    }
    
    // ----- QUERY: POSITIONS -----
    
    /**
     * Gets the position of a specified role  for a specified player.
     * @param roleName the name of the role
     * @param player the player; more precisely its AID
     * @return the position of the specified role (class) for the cpecified player
     */
    public AID getPosition(String roleName, AID player) {
//        System.out.println("----- getPosition() -----");
//        System.out.println("----- roleName: " + roleName + " -----");
//        System.out.println("----- playerAID: " + playerAID.getLocalName() + " -----");
        
        Map<AID, AID> positions = getPositions(roleName);
//        System.out.println("----- positions.size(): " + positions.size() + " -----");
//        for (Map.Entry<AID, AID> entry : positions.entrySet()) {
//            System.out.println("----- " +entry.getKey() + " -> " +entry.getValue() + "-----");
//        }
        
        return positions.get(player);
    }
    
    // TAG NOT-USED
    /**
     * Gets the first position of a specified role (class).
     * @param roleName the name of the role
     * @return the first position of the specified role
     */
    public AID getFirstPosition(String roleName) {
        Map<AID, AID> positions = getPositions(roleName);
        if (!positions.isEmpty()) {
            return getPositionAtIndex(positions, 0);
        } else {
            return null;
        }      
    }
    
    // TAG NOT-USED
    /**
     * Gets a random position of a specified role.
     * @param roleName the name of the role
     * @return a random position of the specified role
     */
    public AID getRandomPosition(String roleName) {
        Map<AID, AID> positions = getPositions(roleName);    
        if (!positions.isEmpty()) {
            int positionIndex = random.nextInt(positions.values().size());
            return getPositionAtIndex(positions, positionIndex);
        } else {
            return null;
        }
    }
    
    // TAG NOT-USED
    /**
     * Gets all role instances of a specified role (class).
     * @param roleName the name of the role (class)
     * @return the set of all role instances of the specified role (class)
     */
    public Set<AID> getAllPositions(String roleName) {
        Map<AID, AID> positions = getPositions(roleName);
        return new HashSet<AID>(positions.values());
    }
    
    // ----- QUERY: PLAYERS -----
    
    /**
     * Queries this organization knowledge base whether
     * a particular player enacts any role.
     * @param player the player; more precisely its AID
     * @return <c>true</c> if the specified player enacts any role;
     *         <c>false</c> otherwise.
     */
    public boolean doesPlayerEnact(AID player) {
        return enactingPlayers.containsKey(player) &&
            enactingPlayers.get(player).isEmployed();
    }
    
    /**
     * Gets all players enacting a role in the organization.
     * @return a set of all players enacting a role in the organization
     */
    public Set<AID> getAllPlayers() {
        return new HashSet(enactingPlayers.keySet());
    }
    
    // ----- UPDATE: ROLES -----
    
    /**
     * Updates this organization knowledge base to contain information that
     * a particular role is being enacted by a particular player.
     * @param roleName the role name
     * @param roleAID the role AID
     * @param player the player; more precisely its AID
     */
    public void updateRoleIsEnacted(String roleName, AID roleAID, AID player) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        assert roleAID != null;
        assert player != null;
        // -------------------------
        
//        System.out.println("----- updateRoleIsEnacted() -----");
//        System.out.println("----- roleName: " + roleName + " -----");
//        System.out.println("----- roleAID: " + roleAID + " -----");
//        System.out.println("----- playerAID: " + playerAID + " -----");
        getPositions(roleName).put(player, roleAID);
        updatePlayerEnactsRole(player, roleName);
    }
    
    /**
     * Updates this organization knowledge base to contain information that
     * a particular role is being deacted by a particular player.
     * @param roleName the role name
     * @param player the player; more precisely its AID
     */
    public void updateRoleIsDeacted(String roleName, AID player) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        assert player != null;
        // -------------------------
        
        getPositions(roleName).remove(player);
        updatePlayerDeactsRole(player, roleName);
    }

    // ----- UPDATE: POSITIONS -----
    
    private static AID getPositionAtIndex(Map<AID, AID> positions, int index) {
        for (AID position : positions.values()) {
            if (index == 0) {
                return position;
            }
            index--;
        }
        return null;
    }
    
    /**
     * Gets the positions of a specified role.
     * @param roleName the name of the role
     * @return the positions of the specified role
     */
    private Map<AID, AID> getPositions(String roleName) {
        if (!enactedRoles.containsKey(roleName)) {
            enactedRoles.put(roleName, new HashMap<AID, AID>());
        }
        return enactedRoles.get(roleName);
    }
    
    // ----- UPDATE: PLAYERS -----
    
    /**
     * Updates the organization knowledge base to contain information that
     * a particular player enacts a particular role.
     * @param player the player; more precisely its AID
     * @param roleName the name of the role
     */
    private void updatePlayerEnactsRole(AID player, String roleName) {
        // ----- Preconditions -----
        assert player != null;
        assert roleName != null && !roleName.isEmpty();
        // ------------------------
        
        addPlayerIfApplicable(player);
        getPlayerDescription(player).enactRole(roleName);
    }

    /**
     * Updates the organization knowledge base to contain information that
     * a particular player deacts a particular role.
     * @param player the player; more precisely its AID
     * @param roleName the name of the role
     */
    private void updatePlayerDeactsRole(AID player, String roleName) {
       // ----- Preconditions -----
        assert player != null;
        assert roleName != null && !roleName.isEmpty();
        // ------------------------

        getPlayerDescription(player).deactRole(roleName);
        removePlayerIfApplicable(player);
    }
    
    /**
     * Adds a player to the enacting players if it is not already there.
     * @param player the player to add
     */
    private void addPlayerIfApplicable(AID player) {
        if (!enactingPlayers.containsKey(player)) {
            enactingPlayers.put(player, new PlayerDescription(player));
        }
    }
    
    /**
     * Removes a player from the enacting players if is not employed.
     * @param player the player to remove
     */
    private void removePlayerIfApplicable(AID player) {
        if (!getPlayerDescription(player).isEmployed()) {
            enactingPlayers.remove(player);
        }
    }
    
    /**
     * Gets the player description for a specified player
     * @param player the player; more precisely its AID
     * @return the player description for the specified player
     */
    private PlayerDescription getPlayerDescription(AID player) {
        return enactingPlayers.get(player);
    }
    
    // </editor-fold> 
}
