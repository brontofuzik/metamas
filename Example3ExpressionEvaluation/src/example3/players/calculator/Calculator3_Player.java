package example3.players.calculator;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Calculator3_Player extends Calculator_Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the Subtractor role.
     */
    private static String SUBTRACTOR_ROLE_FULL_NAME = "expressionEvaluation_Organization.Subtractor_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator3_Player class.
     */
    public Calculator3_Player() {
        super(new RoleFullName(SUBTRACTOR_ROLE_FULL_NAME));
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
