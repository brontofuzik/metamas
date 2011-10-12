package metamas.semanticmodel;

import metamas.semanticmodel.Role;
import metamas.semanticmodel.Skill;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Lukáš Kúdela
 */
public class RoleTest {

    // <editor-fold defaultstate="collapsed" desc="Test">
    
    @Test
    public void testAddRequiredSkill() {
        // ----- Test parameters -----
        
        final String ROLE_NAME = "Test role";
        final String SKILL_NAME = "Test skill";
        
        // ----- Set up -----
        
        Role role = new Role(ROLE_NAME);
        Skill skill = new Skill(SKILL_NAME);
        
        // ----- Exercise -----
        
        role.addRequiredSkill(skill);
        
        // ----- Verify -----
        
        assertTrue(role.requiresSkill(skill));
    }
    
    // </editor-fold>
}
