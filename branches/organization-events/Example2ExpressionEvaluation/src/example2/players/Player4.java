package example2.players;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Player4 extends OperatorPlayer {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the Multiplier role.
     */
    private static String MULTIPLIER_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Multiplier_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator4_Player class.
     */
    public Player4() {
        super(new RoleFullName(MULTIPLIER_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibilities.
        addResponsibility(Multiply_Responsibility.class);
        
        // Schedule behaviours.
        scheduleEnactRole(8000);
    }
    
    // </editor-fold>   
}
