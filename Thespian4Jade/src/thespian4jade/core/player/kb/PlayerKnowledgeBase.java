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
    
    /**
     * The Query view.
     */
    private QueryView queryView = this.new QueryView();
    
    /**
     * The Update view.
     */
    private UpdateView updateView = this.new UpdateView();
    
    /**
     * The enacted roles.
     */
    private Hashtable<String, PositionDescription> enactedRoles = new Hashtable<String, PositionDescription>();
    
    /**
     * The active role.
     */
    private PositionDescription activeRole;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">

    /**
     * Gets the Query view.
     * @return the Query view
     */
    public QueryView query() {
        return queryView;
    }
    
    /**
     * Gets the Update view
     * @return the Update view
     */
    public UpdateView update() {
        return updateView;
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    /**
     * The Query view
     */
    public class QueryView {

        /**
         * Gets enacted positions for a specified role.
         * @param roleName the name of the role
         * @return the enacted positions for the specified role
         */
        public PositionDescription getEnactedPositions(String roleName) {       
            return enactedRoles.get(roleName);
        }

        /**
         * Gets all enacted positions.
         * @return all enacted positions (a sequence)
         */
        public Iterable<PositionDescription> getAllEnactedPositions() {
            return enactedRoles.values();
        }
        
        /**
         * Gets the currently active position.
         * @return the currently active position
         */
        public PositionDescription getActivePosition() {
            return activeRole;
        }

        /**
         * Determines whether the player enacts a role.
         * @param roleName the name of the role
         * @return <c>true</c> if the player enacts the role,
         *     <c>false</c> otherwise
         */
        public boolean doesEnactRole(String roleName) {
            // ----- Preconditions -----
            assert roleName != null && !roleName.isEmpty();
            // -------------------------

            return enactedRoles.containsKey(roleName);
        }

        /**
         * Determines whether the player plays a role.
         * @param roleName the name of the role
         * @return <c>true</c> if the player plays the role,
         *     <c>false</c> otherwise
         */
        public boolean doesPlayRole(String roleName) {
            // ----- Preconditions -----
            assert roleName != null && !roleName.isEmpty();
            // -------------------------

            return activeRole != null ? roleName.equals(activeRole.getRole()) : false;
        }

        /**
         * Determines whether the player can activate a role.
         * @param roleName the name of the role
         * @return <c>true</c> if the player can activate the role,
         *     <c>false</c> otherwise
         */
        public boolean canActivateRole(String roleName) {
            // ----- Preconditions -----
            assert roleName != null && !roleName.isEmpty();
            // -------------------------

            return doesEnactRole(roleName) && !doesPlayRole(roleName);
        }

        /**
         * Determines whether the player can deactivate a role.
         * @param roleName the name of the role
         * @return <c>true</c> if the player can deactivate the role,
         *     <c>false</c> otherwise
         */
        public boolean canDeactivateRole(String roleName) {
            // ----- Preconditions -----
            assert roleName != null && !roleName.isEmpty();
            // -------------------------   

            return doesEnactRole(roleName) && doesPlayRole(roleName);
        }
    }
    
    /**
     * The Update view.
     */
    public class UpdateView {
        
        /**
         * The role is enacted.
         * @param roleName the name of the role
         * @param roleAID the position; more precisely, its AID
         * @param organizationName the local name of the organization
         * @param organizationAID the organization; more precisely, its AID
         */
        public void enactRole(String roleName, AID roleAID, String organizationName, AID organizationAID) {
            PositionDescription roleDescription = new PositionDescription(roleAID, roleName, organizationAID);
            enactedRoles.put(roleName, roleDescription);
        }

        /**
         * A role is deacted.
         * @param roleName the name of the role
         */
        public void deactRole(String roleName) {
            enactedRoles.remove(roleName);
        }

        /**
         * A role is activated.
         * @param roleName the name of the role
         */
        public void activateRole(String roleName) {
            // ----- Preconditions -----
            assert enactedRoles.containsKey(roleName);
            // -------------------------

            activeRole = query().getEnactedPositions(roleName);
        }

        /**
         * A role is deactivated.
         */
        public void deactivateRole() {
            activeRole = null;
        }
    }
    
    // </editor-fold>
}