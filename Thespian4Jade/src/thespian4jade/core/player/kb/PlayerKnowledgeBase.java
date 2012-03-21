package thespian4jade.core.player.kb;

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
    
    private Query query = this.new Query();
    
    private Update update = this.new Update();
    
    private Hashtable<String, RoleDescription> enactedRoles = new Hashtable<String, RoleDescription>();
    
    private RoleDescription activeRole;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    public Query query() {
        return query;
    }
    
    public Update update() {
        return update;
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    public class Query {
        
        public Iterable<RoleDescription> getEnactedRoles() {
            return enactedRoles.values();
        }

        public RoleDescription getEnactedRole(String roleName) {       
            return enactedRoles.get(roleName);
        }

        public RoleDescription getActiveRole() {
            return activeRole;
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

            return enactedRoles.containsKey(roleName);
        }

        public boolean doesPlayRole(String roleName) {
            // ----- Preconditions -----
            assert roleName != null && !roleName.isEmpty();
            // -------------------------

            return activeRole != null ? roleName.equals(activeRole.getRoleName()) : false;
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

            return doesEnactRole(roleName) && doesPlayRole(roleName);
        }

        public boolean canInvokeCompetence(String competenceName) {
            // ----- Preconditions -----
            assert competenceName != null && !competenceName.isEmpty();
            // -------------------------

            // TODO (priority: medium) Implement.
            return true;
        }
    }
    
    public class Update {
        
        public void enactRole(String roleName, AID roleAID, String organizationName, AID organizationAID) {
            RoleDescription roleDescription = new RoleDescription(roleName, roleAID, organizationName, organizationAID);
            enactedRoles.put(roleName, roleDescription);
        }

        public void deactRole(String roleName) {
            enactedRoles.remove(roleName);
        }

        public void activateRole(String roleName) {
            // ----- Preconditions -----
            assert enactedRoles.containsKey(roleName);
            // -------------------------

            activeRole = query().getEnactedRole(roleName);
        }

        public void deactivateRole() {
            activeRole = null;
        }
    }
    
    // </editor-fold>
}
