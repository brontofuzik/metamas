package metamas.semanticmodel;

import metamas.semanticmodel.Position;
import metamas.semanticmodel.SinglePosition;
import metamas.semanticmodel.Role;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lukáš Kúdela
 */
public class SinglePositionTest {
    
    // <editor-fold defaultstate="collapsed" desc="Test suite parameters">
    
    public static final String POSITION_NAME = "Test position";
    
    public static final String ROLE_NAME = "Test role";
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private static Role role;
    
    /** SUT */
    Position position;
      
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Set up & Tear down">
    
    @BeforeClass
    public static void setUpTestFixture() {
        role = new Role(ROLE_NAME);
    }
    
    // </editor-fold>
    
    @Test
    public void testConstructor() {
        // ----- Exercise -----
        
        position = new SinglePosition(POSITION_NAME, role);
        
        // ----- Verify -----
        
        assertEquals(POSITION_NAME, position.getName());
        assertEquals(role, position.getRole());
    }
    
    @Test
    public void testConstructor_emptyName() {
        try {
            // ----- Exercise -----
            
            position = new SinglePosition("", role);
            fail();
        } catch (Throwable throwable) {
            // ----- Verify -----
            
            assertTrue(throwable instanceof IllegalArgumentException);
        }
    }
    
    @Test
    public void testConstructor_nullRole() {
        try {
            // ----- Exercise -----
            
            position = new SinglePosition(POSITION_NAME, null);
            fail();
        } catch (Throwable throwable) {
            // ----- Verify -----
            
            assertTrue(throwable instanceof IllegalArgumentException);
        }
    }
    
    @Test
    public void testGetName() {
        // ----- Set up -----
        
        position = new SinglePosition(POSITION_NAME, role);
        
        // ----- Exercise & Verify -----
        
        assertEquals(POSITION_NAME, position.getName());
    }

    @Test
    public void testGetRole() {
        // ----- Set up -----
        
        position = new SinglePosition(POSITION_NAME, role);
        
        // ----- Exercise & Verify -----
        
        assertEquals(role, position.getRole());
    }
}
