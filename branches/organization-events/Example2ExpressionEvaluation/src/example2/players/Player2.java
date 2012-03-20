package example2.players;

/**
 * Player2 - the player playing the 'Adder' role.
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Player2 extends OperatorPlayer {

    // <editor-fold defaultstate="collapsed" desc="Constant fields">
    
    /**
     * The full name of the Adder role.
     */
    private static String ADDER_ROLE_FULL_NAME
        = "expressionEvaluation_Organization.Adder_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator2_Player class.
     */
    public Player2() {
        super(new RoleFullName(ADDER_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected void setup() {
        super.setup();
        
        // Add responsibilities.
        addResponsibility(Add_Responsibility.class);
        
        // Schedule behaviours.
        scheduleEnactRole(4000);
    }
    
    // </editor-fold>
}
