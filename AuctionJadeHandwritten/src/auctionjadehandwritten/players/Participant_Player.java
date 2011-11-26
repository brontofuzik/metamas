package auctionjadehandwritten.players;

import jadeorg.core.player.Player;
import jadeorg.core.player.PlayerException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A Participant player.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Participant_Player extends Player {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static final Set<String> abilities = new HashSet<String>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    static {
        abilities.add("propose-task"); // Auctioneer
        abilities.add("evaluate-task"); // Bidder
        abilities.add("evaluate-bidders"); // Auctioneer
        abilities.add("execute-task"); // Bidder
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        for (Object argument : getArguments()) {            
            RoleFullName roleFullName = new RoleFullName((String)argument);
            try {
                enactRole(roleFullName.getOrganizationName(), roleFullName.getRoleName());
            } catch (PlayerException ex) {
                log(Level.SEVERE, String.format("Error: %1$s", ex.getMessage()));
            }
        }
    }
    
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if all requirements can be met; <c>false</c> otherwise
     */
    @Override
    protected boolean evaluateRequirements(String[] requirements) {
        return evaluateAllRequirements(requirements);
    }
  
    // ---------- PRIVATE ----------
    
    // TODO Consider moving this method to the Player superclass.
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if all requirement can be met; <c>false</c> otherwise
     */
    private boolean evaluateAllRequirements(String[] requirements) {
        for (String requirement : requirements) {
            if (evaluateRequirement(requirement)) {
                return false;
            }
        }
        return true;
    }
    
    // TODO Consider moving this method to the Player superclass.
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if any requirement can be met; <c>false</c> otherwise
     */
    private boolean evaluteAnyRequirement(String[] requirements) {
        for (String requirement : requirements) {
            if (evaluateRequirement(requirement)) {
                return true;
            }
        }
        return false;
    }
    
    // TODO Consider moving this method to the Player superclass. 
    /**
     * Evaluates a requirement.
     * @param requirement the requirement to evaluate
     * @return <c>true</c> if all requirements can be met; <c>false</c> otherwise 
     */
    private boolean evaluateRequirement(String requirement) {
        return abilities.contains(requirement);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Classes">
    
    private static class RoleFullName {
        
        /** The name of organization instance. */
        private String organizationName;
        
        /** The name of role class. */
        private String roleName;
        
        RoleFullName(String roleFullName) {
            String[] organizationNameAndRoleName = roleFullName.split("\\.");
            organizationName = organizationNameAndRoleName[0];
            roleName = organizationNameAndRoleName[1];
        }
        
        String getOrganizationName() {
            return organizationName;
        }
        
        String getRoleName() {
            return roleName;
        }
    }
    
    // </editor-fold>
}
