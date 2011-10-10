package metamas.semanticmodel;

import metamas.semanticmodel.Skill;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lukáš Kúdela
 */
public class SkillTest {

    // <editor-fold defaultstate="collapsed" desc="Test suite parameters">
    
    private static final String SKILL_NAME = "Test skill";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    Skill skill;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Tests">
    
    @Test
    public void testConstructor() {
        // ----- Exercise -----
        
        skill = new Skill(SKILL_NAME);
        
        // ----- Verify -----
        
        assertEquals(SKILL_NAME, skill.getName());
    }
    
    @Test
    public void testConstructor_emptyName() {
        try {
            // ----- Exercise -----
            
            skill = new Skill("");
            fail();
        } catch (Throwable throwable) {
            // ----- Verify -----
            
            assertTrue(throwable instanceof IllegalArgumentException);
        }
    }
        
    @Test
    public void testGetName() {
        // ----- Set up -----
        
        skill = new Skill(SKILL_NAME);
        
        // ----- Exercise & Verify -----
        
        assertEquals(SKILL_NAME, skill.getName());   
    }
    
    // </editor-fold>
}
