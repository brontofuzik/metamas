package jadeorg.player;

import java.util.Hashtable;

/**
 * A player knowledge base.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
class PlayerKnowledgeBase {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Hashtable<String, RoleInfo> enactedRoles = new Hashtable<String, RoleInfo>();
    
    private String activeRole;
    
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
    
    RoleInfo getRoleInfo(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        return enactedRoles.get(roleName);
    }
    
    RoleInfo getActiveRoleInfo() {
        return getRoleInfo(activeRole);
    }
    
    // ----- UPDATE -----
    
    // </editor-fold>
}
