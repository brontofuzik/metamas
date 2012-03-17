package example3.players.calculator;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Calculator4_Player extends Calculator_Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the Multiplier role.
     */
    private static String MULTIPLIER_ROLE_FULL_NAME = "expressionEvaluation_Organization.Multiplier_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator4_Player class.
     */
    public Calculator4_Player() {
        super(new RoleFullName(MULTIPLIER_ROLE_FULL_NAME));
    }
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Methods">
    
    @Override
    protected int doScheduleCompetenceInvocations(int timeout) {
        // No competences to invoke.
        return timeout + 4000;
    }
    
    // </editor-fold>   
}
