package metamas.semanticmodel;

import metamas.semanticmodel.AgentClass;
import metamas.semanticmodel.Agent;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Lukáš Kúdela
 */
public class AgentTest {
    
    // <editor-fold defaultstate="collapsed" desc="Test parameters">
    
    private static final String AGENT_CLASS_NAME = "Test agent class";
    
    private static final String AGENT_NAME = "Test agent";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static AgentClass agentClass;
    
    private Agent agent;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Set up & Tear down">
    
    @BeforeClass
    public static void testFixtureSetUp() {
        agentClass = new AgentClass(AGENT_CLASS_NAME);
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Tests">
    
    @Test
    public void testConstructor() {
        // ----- Exercise -----
        
        agent = new Agent(AGENT_NAME, agentClass);
        
        // ----- Verify -----
        
        assertEquals(AGENT_NAME, agent.getName());
        assertEquals(agentClass, agent.getKlass());
    }
    
    @Test
    public void testConstructor_emptyName() {
        try {
            // ----- Exercise -----
            
            agent = new Agent("", agentClass);
            fail();
        } catch (Throwable throwable) {
            // ----- Verify -----
            
            assertTrue(throwable instanceof IllegalArgumentException);
        }
    }

    @Test
    public void testGetName() {
        // ----- Set up -----
        
        agent = new Agent(AGENT_NAME, agentClass);
        
        // ----- Exercise & Verify -----
        
        assertEquals(AGENT_NAME, agent.getName());
    }

    @Test
    public void testGetKlass() {
        // ----- Set up -----
        
        agent = new Agent(AGENT_NAME, agentClass);
        
        // ----- Exercise & Verify -----
        
        assertEquals(agentClass, agent.getKlass());
    }
    
    // </editor-fold>
}
