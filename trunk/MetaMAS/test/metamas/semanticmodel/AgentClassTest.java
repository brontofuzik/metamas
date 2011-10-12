package metamas.semanticmodel;

import metamas.semanticmodel.AgentClass;
import metamas.semanticmodel.Agent;
import metamas.semanticmodel.Skill;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Lukáš Kúdela
 */
public class AgentClassTest {
    
    // <editor-fold defaultstate="collapsed" desc="Test case parameters">
    
    private final String AGENT_CLASS_NAME = "TestAgentClass";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private AgentClass agentClass;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Set up & Tear down">
    
    @Before
    public void setUp() {
        agentClass = new AgentClass(AGENT_CLASS_NAME);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Tests">
    
    @Test
    public void testAddPosessedSkill() {
        // ----- Test parameters -----
        
        final String SKILL_NAME = "Test skill";
        
        // ----- Set up -----
        
        Skill skill = new Skill(SKILL_NAME);
        
        // ----- Exercise -----
        
        agentClass.addPosessedSkill(skill);
        
        // ----- Verify -----
        
        assertTrue(agentClass.possessesSkill(skill));
    }
    
    @Test
    public void testCreateAgent() {
        // ----- Test parameters -----

        final String AGENT_NAME = "Test agent";

        // ----- Exercise -----

        Agent agent = agentClass.createAgent(AGENT_NAME);

        // ----- Verify -----

        assertEquals(AGENT_NAME, agent.getName());
        assertEquals(agentClass, agent.getKlass());
    }
    
    // </editor-fold>
}