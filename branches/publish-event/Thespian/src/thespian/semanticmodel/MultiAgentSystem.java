package thespian.semanticmodel;

import java.util.HashMap;
import java.util.Map;
import thespian.semanticmodel.organization.Organization;
import thespian.semanticmodel.organization.OrganizationType;
import thespian.semanticmodel.player.Player;
import thespian.semanticmodel.player.PlayerType;
import thespian.semanticmodel.protocol.Protocol;
import thespian.utilities.Assert;

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
    
    private Map<String, OrganizationType> organizationTypes = new HashMap<String, OrganizationType>();
    
    private Map<String, Organization> organizations = new HashMap<String, Organization>();
    
    private Map<String, PlayerType> playerTypes = new HashMap<String, PlayerType>();
    
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
    
    public void addOrganizationType(OrganizationType organizationType) {
        // ----- Preconditions -----
        assert organizationType != null;
        // -------------------------
        
        organizationTypes.put(organizationType.getName(), organizationType);
    }
    
    public void addOrganization(Organization organization) {
        // ----- Preconditions -----
        assert organization != null;
        // -------------------------
        
        organizations.put(organization.getName(), organization);
    }
    
    public void addPlayerType(PlayerType playerType) {
        // ----- Preconditions -----
        assert playerType != null;
        // -------------------------
        
        playerTypes.put(playerType.getName(), playerType);
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
