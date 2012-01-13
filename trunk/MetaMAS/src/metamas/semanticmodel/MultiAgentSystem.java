package metamas.semanticmodel;

import java.util.HashMap;
import java.util.Map;
import metamas.semanticmodel.organization.Organization;
import metamas.semanticmodel.organization.OrganizationClass;
import metamas.semanticmodel.player.Player;
import metamas.semanticmodel.player.PlayerClass;
import metamas.semanticmodel.protocol.Protocol;
import metamas.utilities.Assert;

/**
 * A multiagent system.
 * @author Lukáš Kúdela
 * @since 2012-01-10
 * @version %I% %G%
 */
public class MultiAgentSystem {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private String name;
    
    private String rootNamespace;
    
    private Map<String, OrganizationClass> organizationClasses = new HashMap<String, OrganizationClass>();
    
    private Map<String, Organization> organizations = new HashMap<String, Organization>();
    
    private Map<String, PlayerClass> playerClasses = new HashMap<String, PlayerClass>();
    
    private Map<String, Player> players = new HashMap<String, Player>();
    
    private Map<String, Protocol> protocols = new HashMap<String, Protocol>();
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    public MultiAgentSystem(String name, String rootNamespace) {
        // ----- Preconditions -----
        Assert.isNotEmpty(name, "name");
        Assert.isNotEmpty(rootNamespace, "rotNamespace");
        // -------------------------
        
        this.name = name;
        this.rootNamespace = rootNamespace;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters and setters">
    
    public String getName() {
        return name;
    }

    public String getRootNamespace() {
        return rootNamespace;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public void addOrganizationClass(OrganizationClass organizationClass) {
        // ----- Preconditions -----
        assert organizationClass != null;
        // -------------------------
        
        organizationClasses.put(organizationClass.getName(), organizationClass);
    }
    
    public void addOrganization(Organization organization) {
        // ----- Preconditions -----
        assert organization != null;
        // -------------------------
        
        organizations.put(organization.getName(), organization);
    }
    
    public void addPlayerClass(PlayerClass playerClass) {
        // ----- Preconditions -----
        assert playerClass != null;
        // -------------------------
        
        playerClasses.put(playerClass.getName(), playerClass);
    }
    
    public void addPlayer(Player player) {
        // ----- Preconditions -----
        assert player != null;
        // -------------------------
        
        players.put(player.getName(), player);
    }
    
    public void addProtocol(Protocol protocol) {
        // ----- Preconditions -----
        assert protocol != null;
        // -------------------------
        
        protocols.put(protocol.getName(), protocol);
    }
    
    public void generate(String string) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    // </editor-fold>
}
