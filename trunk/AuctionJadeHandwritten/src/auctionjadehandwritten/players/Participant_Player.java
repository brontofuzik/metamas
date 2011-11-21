package auctionjadehandwritten.players;

import jadeorg.core.player.Player;
import java.util.HashSet;
import java.util.Set;

/**
 * A Participant player.
 * @author Lukáš Kúdela
 * @since 2011-11-20
 * @version %I% %G%
 */
public class Participant_Player extends Player {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static Set<String> abilities = new HashSet<String>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    static {
        abilities.add("task"); // Auctioneer
        abilities.add("propose"); // Bidder
        abilities.add("evaluate"); // Auctioneer
        abilities.add("execute"); // Bidder
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if all requirements can be met; <c>false</c> otherwise
     */
    @Override
    protected boolean evaluateRequirements(String[] requirements) {
        for (String requirement : requirements) {
            if (evaluateRequirement(requirement)) {
                return false;
            }
        }
        return true;
    }
  
    /**
     * Evaluates a set of requirements.
     * @param requirements the set of requirements to evaluate
     * @return <c>true</c> if any requirement can be met; <c>false</c> otherwise
     */
    protected boolean evaluteAnyRequirement(String[] requirements) {
        for (String requirement : requirements) {
            if (evaluateRequirement(requirement)) {
                return true;
            }
        }
        return false;
    }
    
    // ---------- PRIVATE ----------
    
    /**
     * Evaluates a requirement.
     * @param requirement the requirement to evaluate
     * @return <c>true</c> if all requirements can be met; <c>false</c> otherwise 
     */
    private boolean evaluateRequirement(String requirement) {
        return abilities.contains(requirement);
    }
    
    // </editor-fold>
}
