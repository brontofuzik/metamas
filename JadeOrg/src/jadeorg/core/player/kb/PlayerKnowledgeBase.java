package jadeorg.core.player.kb;

import jadeorg.core.player.kb.RoleDescription;
import jade.core.AID;
import java.util.Hashtable;

/**
 * A player knowledge base.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class PlayerKnowledgeBase {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private Hashtable<String, RoleDescription> enactedRoles = new Hashtable<String, RoleDescription>();
    
    private String activeRole;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    // ----- QUERY -----
    
    public AID getRoleAID(String roleName) {
        return getRoleDescription(roleName).getRoleAID();
    }
    
    /**
     * Queries this player knowledge base whether the
     * player enacts a particular role.
     * @param roleName the name of the role
     * @return <c>true</c> if the player enacts the specified role;
     *         <c>false</c> otherwise.
     */
    public boolean doesEnactRole(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        return enactedRoles.contains(roleName);
    }
    
    public boolean doesPlayRole(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        return roleName.equals(activeRole);
    }
    
        public boolean canActivateRole(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        return doesEnactRole(roleName) && !doesPlayRole(roleName);
    }
    
    public boolean canDeactivateRole(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------   
    
        return doesPlayRole(roleName);
    }
    
    // ----- UPDATE -----
    
    public void enactRole(AID roleAID, AID organizationAID) {
        RoleDescription roleDescription = new RoleDescription(roleAID, organizationAID);
        enactedRoles.put(roleDescription.getRoleName(), roleDescription);
    }
    
    public void deactRole(String roleName) {
        enactedRoles.remove(roleName);
    }
    
    public void activateRole(String roleName) {
        // ----- Preconditions -----
        assert enactedRoles.contains(roleName);
        // -------------------------
        
        activeRole = roleName;
    }
    
    public void deactivateRole() {
        activeRole = null;
    }
    
    // ---------- PRIVATE ----------
    
    private RoleDescription getRoleDescription(String roleName) {
        // ----- Preconditions -----
        assert roleName != null && !roleName.isEmpty();
        // -------------------------
        
        return enactedRoles.get(roleName);
    }
    
    private RoleDescription getActiveRoleDescription() {
        return getRoleDescription(activeRole);
    }
    
    // </editor-fold>
}
