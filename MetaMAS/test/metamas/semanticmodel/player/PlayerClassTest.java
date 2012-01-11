package metamas.semanticmodel.player;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Lukáš Kúdela
 */
public class PlayerClassTest {
    
    // <editor-fold defaultstate="collapsed" desc="Test case parameters">
    
    private final String AGENT_CLASS_NAME = "TestAgentClass";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private PlayerClass agentClass;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Set up & Tear down">
    
    @Before
    public void setUp() {
        agentClass = new PlayerClass(AGENT_CLASS_NAME);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Tests">
    
    @Test
    public void testAddPosessedSkill() {
        // ----- Test parameters -----
        
        final String SKILL_NAME = "Test skill";
        
        // ----- Set up -----
        
//        Skill skill = new Skill(SKILL_NAME);
        
        // ----- Exercise -----
        
//        agentClass.addPosessedSkill(skill);
        
        // ----- Verify -----
        
 //       assertTrue(agentClass.possessesSkill(skill));
    }
    
    @Test
    public void testCreateAgent() {
        // ----- Test parameters -----

        final String AGENT_NAME = "Test agent";

        // ----- Exercise -----

        Player agent = agentClass.createPlayer(AGENT_NAME);

        // ----- Verify -----

        assertEquals(AGENT_NAME, agent.getName());
        assertEquals(agentClass, agent.getKlass());
    }
    
    // </editor-fold>
}