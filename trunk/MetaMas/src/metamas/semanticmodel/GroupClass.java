package metamas.semanticmodel;

import metamas.semanticmodel.interactionprotocol.InteractionProtocol;
import java.util.HashMap;
import java.util.Map;
import metamas.utilities.Assert;

/**
 * A group classifier.
 * @author Lukáš Kúdela
 */
public class GroupClass {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    /** The name of the group. */
    private String name;
    
    private Map<String, Role> roles =
        new HashMap<String, Role>();

    /** The positions within the group. */
    private Map<String, Position> positions =
        new HashMap<String, Position>();
    
    /** The interaction protocols within the group. */
    private Map<String, InteractionProtocol> interactionProtocols =
        new HashMap<String, InteractionProtocol>();

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public GroupClass(String name) {
        this.name = name;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return name;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public Role getRole(String name) {
        Assert.isNotEmpty(name, "name");
        
        return roles.get(name);
    }

    /**
     * Adds a position.
     * @param  position
     */
    public void addPosition(Position position) {
        Assert.isNotNull(position, "position");
        
        addRole(position.getRole());
        positions.put(position.getName(), position);
    }
    
    // TAG Convenience method
    /**
     * Adds a single position.
     * @param name
     * @param role
     */
    public void addSinglePosition(String name, Role role) {
        addPosition(new SinglePosition(name, role));
    }
    
    // TAG Convenience method
    /**
     * Adds a multi-position.
     * @param name
     * @param role
     */
    public void addMultiPosition(String name, Role role) {
       addPosition(new MultiPosition(name, role));
    }
    
    /**
     * Gets a position by its name.
     * @param name The name of the position to get.
     * @return The position specified by its name,
     * or null if no such position exists in this group class.
     */
    public Position getPosition(String name) {
        Assert.isNotEmpty(name, "name");
        
        return positions.get(name);
    }
    
    // TAG Convenience method
    /**
     * Gets a single position by its name.
     * @param name The name of the single position to get.
     * @return The single position specified by its name,
     *   or null if no such single position exists in this group class.
     */
    public SinglePosition getSinglePosition(String name) {
        return (SinglePosition)getPosition(name);
    }
    
    // TAG Convenience method
    /**
     * Gets a multi-position by its name.
     * @param name The name of the multi-position to get.
     * @return The multi-position specified by its name,
     *   or null if no such multi-position exists in this group class.
     */
    public MultiPosition getMultiPosition(String name) {
        return (MultiPosition)getPosition(name);
    }
    
    /**
     * Adds an interaction protocol.
     */
    public void addInteractionProtocol(InteractionProtocol interactionProtocol) {
        Assert.isNotNull(interactionProtocol, "interactionProtocol");
     
        interactionProtocols.put(interactionProtocol.getName(), interactionProtocol);
    }

    /**
     * Creates an instance of this group class - a concrete group.
     * @param name The name of the group instance to create.
     */
    public Group createGroup(String name) {
        Assert.isNotEmpty(name, "name");

        return new Group(name, this);
    }
    
    // ---------- PRIVATE ----------

    private void addRole(Role role) {
        assert role != null;
        
        if (!roles.containsKey(role.getName())) {
            roles.put(role.getName(), role);
        }
    }
    
    // </editor-fold>
}
