package example2.players.calculator;

/**
 * @author Lukáš Kúdela
 * @since 2012-03-14
 * @version %I% %G%
 */
public class Calculator2_Player extends Calculator_Player {

    // <editor-fold defaultstate="collapsed" desc="Fields">
    
    /**
     * The full name of the Adder role.
     */
    private static String ADDER_ROLE_FULL_NAME = "expressionEvaluation_Organization.Adder_Role";
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    
    /**
     * Initializes a new instane of the Calculator2_Player class.
     */
    public Calculator2_Player() {
        super(new RoleFullName(ADDER_ROLE_FULL_NAME));
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
