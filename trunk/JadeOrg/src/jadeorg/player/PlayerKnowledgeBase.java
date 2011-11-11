package jadeorg.player;

import jade.core.AID;
import java.util.Hashtable;

/**
 * A player knowledge base.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
class PlayerKnowledgeBase {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Hashtable<String, RoleDescription> enactedRoles = new Hashtable<String, RoleDescription>();
    
    private RoleDescription activeRole;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    // ----- QUERY -----
    
    boolean canActivateRole(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        return doesEnactRole(roleName) && !doesPlayRole(roleName);
    }
    
    boolean canDeactivateRole(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------   
    
        return doesPlayRole(roleName);
    }
    
    /**
     * Queries this player knowledge base whether the
     * player enacts a particular role.
     * @param roleName the name of the role
     * @return <c>true</c> if the player enacts the specified role;
     *         <c>false</c> otherwise.
     */
    boolean doesEnactRole(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        return enactedRoles.contains(roleName);
    }
    
    boolean doesPlayRole(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        return roleName.equals(activeRole);
    }
    
    RoleDescription getRoleDescription(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        return enactedRoles.get(roleName);
    }
    
    RoleDescription getActiveRoleDescription() {
        return activeRole;
    }
    
    // ----- UPDATE -----
    
    void enactRole(AID roleAID, AID organizationAID) {
        enactRole(new RoleDescription(roleAID, organizationAID));
    }
    
    void enactRole(RoleDescription roleDescription) {
        enactedRoles.put(roleDescription.getRoleName(), roleDescription);
    }
    
    void deactRole(String roleName) {
        enactedRoles.remove(roleName);
    }
            
    void deactRole(RoleDescription roleDescription) {
        deactRole(roleDescription.getRoleName());
    }
    
    void activateRole(String roleName) {
        activateRole(getRoleDescription(roleName));
    }
    
    void activateRole(RoleDescription roleDescription) {
        activeRole = roleDescription;
    }
    
    void deactivateRole() {
        activeRole = null;
    }
    
    // </editor-fold>
}
