package thespian4jade.core.player.kb;

import jade.core.AID;
import java.util.Hashtable;
import java.util.Map;

/**
 * A role description.
 * @author Lukáš Kúdela
 * @since 2011-10-29
 * @version %I% %G%
 */
public class RoleDescription {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /** The name of the role. */
    private String roleName;
    
    /** The role AID. */
    private AID roleAID;
    
    /** The name of the organization. */
    private String organizationName;
    
    /** The organization AID. */
    private AID organizationAID;
    
    private Map<String, Object> competenceResults = new Hashtable<String, Object>();

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public RoleDescription(String roleName, AID roleAID, String organizationName, AID organizationAID) {
        this.roleName = roleName;
        this.roleAID = roleAID;
        
        this.organizationName = organizationName;
        this.organizationAID = organizationAID;      
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    
    public AID getOrganizationAID() {
        return organizationAID;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public AID getRoleAID() {
        return roleAID;
    }

    public String getRoleName() {
        return roleName;
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public Iterable<String> getCompetences() {
        // TODO (priority: medium) Implement.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    public void saveCompetenceResult(String competenceName, Object competenceResult) {
        competenceResults.put(competenceName, competenceResult);
    }

    public Object loadCompetenceResult(String competenceName) {
        return competenceResults.get(competenceName);
    }
    
    // </editor-fold>
}
