package example2.players.participant;

import jadeorg.core.player.Player;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * A Participant player.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Participant_Player extends Player {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    protected static final Set<String> abilities = new HashSet<String>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    static {
        abilities.add("bid"); // For the Bidder role.
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    int getTimeout() {
        return new Integer((String)getArguments()[0]).intValue();
    }
    
    List<RoleFullName> getRoles() {
        List<RoleFullName> roles = new LinkedList<RoleFullName>();
        for (int i = 1; i < getArguments().length; i++) {
            roles.add(new RoleFullName((String)getArguments()[i]));
        }
        return roles;
    }
            
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add the requirements.
        
        int timeout = getTimeout();
    }
    
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if all requirements can be met; <c>false</c> otherwise
     */
    @Override
    public boolean evaluateRequirements(String[] requirements) {
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
    // </editor-fold>
}
