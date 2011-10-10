package metamas.semanticmodel;

import metamas.semanticmodel.Position;
import metamas.semanticmodel.SinglePosition;
import metamas.semanticmodel.Role;
import metamas.semanticmodel.GroupClass;
import metamas.semanticmodel.Group;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Lukáš Kúdela
 */
public class GroupClassTest {
    
    // <editor-fold defaultstate="collapsed" desc="Test case parameters">
    
    private final String GROUP_CLASS_NAME = "TestGroupClass";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    private GroupClass groupClass;
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Set up & Tear down">
    
    @Before
    public void setUp() {
        groupClass = new GroupClass(GROUP_CLASS_NAME);
    }
        
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Tests">
    
    @Test
    public void testAddSinglePosition() {
        // ----- Test parameters -----
        
        final String ROLE_NAME = "Test role";
        final String POSITION_NAME = "Test position";
        
        // ----- Set up -----
        
        Role role = new Role(ROLE_NAME);
        Position position = new SinglePosition(POSITION_NAME, role);
        
        // ----- Exercise -----
        
        groupClass.addPosition(position);
        
        // ----- Verify -----
        
        Position actualPosition = groupClass.getPosition(POSITION_NAME);
        assertEquals(position, actualPosition);
    }
    
    @Test
    public void testGetPosition() {
        // ----- Test parameters -----
        
        final String ROLE_NAME = "Test role";
        final String POSITION_NAME = "Test position";
        
        // ----- Set up -----
        
        Role role = new Role(ROLE_NAME);
        Position position = new SinglePosition(POSITION_NAME, role);
        groupClass.addPosition(position);
        
        // ----- Exercise -----
        
        Position actualPosition = groupClass.getPosition(POSITION_NAME);
        
        // ----- Verify -----
        
        assertEquals(position, actualPosition);
    }

    @Test
    public void testCreateGroup() {
        // ----- Test parameters -----

        final String GROUP_NAME = "Test group";

        // ----- Exercise -----

        Group group = groupClass.createGroup(GROUP_NAME);

        // ----- Verify -----

        assertEquals(GROUP_NAME, group.getName());
        assertEquals(groupClass, group.getKlass());
    }
    
    // </editor-fold>
}
