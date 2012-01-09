package metamas.semanticmodel;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Lukáš Kúdela
 */
public class GroupTest {
    
    // <editor-fold defaultstate="collapsed" desc="Test case parameters">
    
    private static final String GROUP_CLASS_NAME = "Test group class";
    
    private static final String POSITION_NAME = "Test position";  
    
    private static final String ROLE_NAME = "Test role";  
    
    private static final String GROUP_NAME = "Test group";  
    
    private static final String AGENT_CLASS_NAME = "Test agent class"; 
    
    private static final String AGENT_NAME = "Test agent";
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static GroupClass groupClass;
    
    private static Group group;
    
    private static AgentClass agentClass;
    
    private static Agent agent;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Set up & Tear down">
    
    @BeforeClass
    public static void setUpTestFixture() {
        groupClass = new GroupClass(GROUP_CLASS_NAME);
        Role role = new Role(ROLE_NAME);
        groupClass.addSinglePosition(POSITION_NAME, role);
        
        agentClass = new AgentClass(AGENT_CLASS_NAME);
        agent = agentClass.createAgent(AGENT_NAME);
    }
    
    @Before
    public void setUp() {
        group = groupClass.createGroup(GROUP_NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Tests">
    
    @Test
    public void testAddAgent() {
        // ----- Exercise -----
        
        group.addAgent(POSITION_NAME, agent);
        
        // ----- Verify -----
        
        Agent actualAgent = group.getAgent(POSITION_NAME);
        assertEquals(agent, actualAgent);
    }
        
    @Test
    public void testAddAgent_positionNotDefined() {
        // ----- Test parameters -----
        
        final String UNDEFINED_POSITION_NAME = "Undefined test position";
        
        // ----- Exercise -----
         
        try {         
            group.addAgent(UNDEFINED_POSITION_NAME, agent);
            fail();
        } catch (Error error) {
            //assertTrue(error instanceof PositionNotDefinedError);
        }
        
        // ----- Verify -----
    }
    
    @Test
    public void testAddAgent_positionAlreadyOccupied() {
        // ----- Test parameters -----
        
        final String AGENT2_NAME = "Test agent 2";
        
        // ----- Set up -----
        
        group.addAgent(POSITION_NAME, agent);
        
        // ----- Exercise -----
        
        Agent agent2 = agentClass.createAgent(AGENT2_NAME);
        try {
            group.addAgent(POSITION_NAME, agent2);
            fail();
        } catch (Error error) {
            // ----- Verify -----
            
            //assertTrue(error instanceof PositionAlreadyOccupiedError);
        }
    }
    
    // </editor-fold>
}
