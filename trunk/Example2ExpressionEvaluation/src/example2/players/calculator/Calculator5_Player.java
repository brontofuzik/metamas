package example2.players.calculator;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Calculator5_Player extends Calculator_Player {
    
    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the Divider role.
     */
    private static String DIVIDER_ROLE_FULL_NAME = "expressionEvaluation_Organization.Divider_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator5_Player class.
     */
    public Calculator5_Player() {
        super(new RoleFullName(DIVIDER_ROLE_FULL_NAME));
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
