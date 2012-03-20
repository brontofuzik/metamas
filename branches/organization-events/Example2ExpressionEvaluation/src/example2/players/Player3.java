package example2.players;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Player3 extends OperatorPlayer {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the Subtractor role.
     */
    private static String SUBTRACTOR_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Subtractor_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator3_Player class.
     */
    public Player3() {
        super(new RoleFullName(SUBTRACTOR_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibilities.
        addResponsibility(Subtract_Responsibility.class);
        
        // Schedule behaviours.
        scheduleEnactRole(6000);
    }
    
    // </editor-fold>   
}
