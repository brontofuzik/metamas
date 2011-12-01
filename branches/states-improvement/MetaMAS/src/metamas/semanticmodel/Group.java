package metamas.semanticmodel;

import java.util.HashMap;
import java.util.Map;
import metamas.utilities.Assert;
import metamas.semanticmodel.GroupClass;
import metamas.semanticmodel.Position;
import metamas.exceptions.PositionAlreadyOccupiedError;
import metamas.exceptions.PositionNotDefinedError;

/**
 * A concrete group - an instance of a group class.
 * @author Lukáš Kúdela
 */
public class Group {

    // <editor-fold defaultstate="collapsed" desc="Fields">

    private String name;

    private GroupClass klass;
    
    private Map<String, Agent> agents = new HashMap<String, Agent>();

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Constructors">

    public Group(String name, GroupClass klass) {
        Assert.isNotEmpty(name, "name");
        Assert.isNotNull(klass, "klass");

        this.name = name;
        this.klass = klass;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Getters & Setters">

    public String getName() {
        return name;
    }

    public GroupClass getKlass() {
        return klass;
    }

    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    public Agent getAgent(String positionName) {
        Assert.isNotEmpty(positionName, "positionName");
        
        return agents.get(positionName);
    }
    
    public Agent getAgent(Position position) {
        Assert.isNotNull(position, "position");
        
        return getAgent(position.getName());
    }
    
    public void addAgent(String positionName, Agent agent) {
        Assert.isNotEmpty(positionName, "positionName");
        Assert.isNotNull(agent, "agent");
        
        // Assert that the position is defined for this group.
        if (!isPositionDefined(positionName)) {
            throw new PositionNotDefinedError(positionName, this);
        }
        
        // Assert that the position is not already occupied.
        if (isPositionOccupied(positionName)) {
            throw new PositionAlreadyOccupiedError(positionName, this);
        }
        
        agents.put(positionName, agent);
    }
    
    public void addAgent(Position position, Agent agent) {
        Assert.isNotNull(position, "position");
        Assert.isNotNull(agent, "agent");
        
        addAgent(position.getName(), agent);
    }
    
    // ---------- PRIVATE ----------

    private Position getPosition(String positionName) {
        assert !positionName.isEmpty();
        
        return klass.getPosition(positionName);       
    }
    
    private boolean isPositionDefined(String positionName) {
        assert !positionName.isEmpty();
        
        return getPosition(positionName) != null;
    }
    
    private boolean isPositionOccupied(String positionName) {
        assert !positionName.isEmpty();
        
        return agents.containsKey(positionName);
    }
    
    // </editor-fold>
}
